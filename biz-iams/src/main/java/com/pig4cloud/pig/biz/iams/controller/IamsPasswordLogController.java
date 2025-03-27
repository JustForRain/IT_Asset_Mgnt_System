package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsPasswordLogDto;
import com.pig4cloud.pig.biz.iams.entity.IamsAccountEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.IamsAccountService;
import com.pig4cloud.pig.biz.iams.service.IamsAssetService;
import com.pig4cloud.pig.biz.iams.service.IamsShelfService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.biz.iams.entity.IamsPasswordLogEntity;
import com.pig4cloud.pig.biz.iams.service.IamsPasswordLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 密码修改记录
 *
 * @author pig
 * @date 2025-03-25 17:35:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsPasswordLog" )
@Tag(description = "iamsPasswordLog" , name = "密码修改记录管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsPasswordLogController {

    private final  IamsPasswordLogService iamsPasswordLogService;
	private final IamsAccountService iamsAccountService;
	private final IamsShelfService iamsShelfService;
	private final IamsAssetService iamsAssetService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsPasswordLog 密码修改记录
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_view')" )
    public R getIamsPasswordLogPage(@ParameterObject Page page, @ParameterObject IamsPasswordLogDto iamsPasswordLog) {
        LambdaQueryWrapper<IamsPasswordLogEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(ObjUtil.isNotNull(iamsPasswordLog.getNewPassword()), IamsPasswordLogEntity::getNewPassword, iamsPasswordLog.getNewPassword());
		wrapper.eq(ObjUtil.isNotNull(iamsPasswordLog.getOldPassword()), IamsPasswordLogEntity::getOldPassword, iamsPasswordLog.getOldPassword());
		//帐号条件查询
		if(StrUtil.isNotBlank(iamsPasswordLog.getAccount())){
			List<IamsAccountEntity> list = iamsAccountService.list(Wrappers.lambdaQuery(IamsAccountEntity.class).eq(IamsAccountEntity::getAccount, iamsPasswordLog.getAccount()));
			wrapper.in(!list.isEmpty(), IamsPasswordLogEntity::getAccountId, list.stream().map(IamsAccountEntity::getId).toList());
		}
		//设备角色条件查询
		if(StrUtil.isNotBlank(iamsPasswordLog.getRole())){
			List<IamsShelfEntity> list = iamsShelfService.list(Wrappers.lambdaQuery(IamsShelfEntity.class).eq(IamsShelfEntity::getRole, iamsPasswordLog.getRole()));
			wrapper.in(!list.isEmpty(), IamsPasswordLogEntity::getAssetId, list.stream().map(IamsShelfEntity::getAssetId).toList());
		}
		//序列号
		if(StrUtil.isNotBlank(iamsPasswordLog.getSn())){
			List<IamsAssetEntity> list = iamsAssetService.list(Wrappers.lambdaQuery(IamsAssetEntity.class).eq(IamsAssetEntity::getSn, iamsPasswordLog.getSn()));
			wrapper.in(!list.isEmpty(), IamsPasswordLogEntity::getAssetId, list.stream().map(IamsAssetEntity::getId).toList());
		}
		Page resultPage = iamsPasswordLogService.page(page, wrapper);
		List records = resultPage.getRecords();
		List list = records.stream().map(iamsPasswordLogEntity -> {
			IamsPasswordLogDto iamsPasswordLogDto = BeanUtil.copyProperties(iamsPasswordLogEntity, IamsPasswordLogDto.class);
			IamsAccountEntity accountEntity = iamsAccountService.getById(iamsPasswordLogDto.getAccountId());
			iamsPasswordLogDto.setAccount(accountEntity.getAccount());
			IamsShelfEntity shelfEntity = iamsShelfService.getByAssetId(iamsPasswordLogDto.getAssetId());
			iamsPasswordLogDto.setRole(shelfEntity.getRole());
			return iamsPasswordLogDto;
		}).toList();
		resultPage.setRecords(list);
		return R.ok(resultPage);
    }


    /**
     * 通过id查询密码修改记录
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(iamsPasswordLogService.getById(id));
    }

    /**
     * 新增密码修改记录
     * @param iamsPasswordLog 密码修改记录
     * @return R
     */
    @Operation(summary = "新增密码修改记录" , description = "新增密码修改记录" )
    @SysLog("新增密码修改记录" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_add')" )
    public R save(@RequestBody IamsPasswordLogEntity iamsPasswordLog) {
        return R.ok(iamsPasswordLogService.save(iamsPasswordLog));
    }

    /**
     * 修改密码修改记录
     * @param iamsPasswordLog 密码修改记录
     * @return R
     */
    @Operation(summary = "修改密码修改记录" , description = "修改密码修改记录" )
    @SysLog("修改密码修改记录" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_edit')" )
    public R updateById(@RequestBody IamsPasswordLogEntity iamsPasswordLog) {
        return R.ok(iamsPasswordLogService.updateById(iamsPasswordLog));
    }

    /**
     * 通过id删除密码修改记录
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除密码修改记录" , description = "通过id删除密码修改记录" )
    @SysLog("通过id删除密码修改记录" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(iamsPasswordLogService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param iamsPasswordLog 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_export')" )
    public List<IamsPasswordLogEntity> export(IamsPasswordLogEntity iamsPasswordLog,Long[] ids) {
        return iamsPasswordLogService.list(Wrappers.lambdaQuery(iamsPasswordLog).in(ArrayUtil.isNotEmpty(ids), IamsPasswordLogEntity::getId, ids));
    }
}