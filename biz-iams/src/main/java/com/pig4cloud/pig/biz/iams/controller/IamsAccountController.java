package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsAccountDto;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.IamsAssetService;
import com.pig4cloud.pig.biz.iams.service.IamsShelfService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.biz.iams.entity.IamsAccountEntity;
import com.pig4cloud.pig.biz.iams.service.IamsAccountService;
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
 * 账户
 *
 * @author pig
 * @date 2025-03-25 16:00:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsAccount" )
@Tag(description = "iamsAccount" , name = "账户管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsAccountController {

    private final  IamsAccountService iamsAccountService;
	private final IamsShelfService iamsShelfService;
	private final IamsAssetService iamsAssetService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsAccount 账户
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsAccount_view')" )
    public R getIamsAccountPage(@ParameterObject Page page, @ParameterObject IamsAccountEntity iamsAccount) {
        LambdaQueryWrapper<IamsAccountEntity> wrapper = Wrappers.lambdaQuery();
		Page resultPage = iamsAccountService.page(page, wrapper);
		List list = resultPage.getRecords().stream().map(iamsAccountEntity -> {
			IamsAccountDto iamsAccountDto = BeanUtil.copyProperties(iamsAccountEntity, IamsAccountDto.class);
			IamsShelfEntity shelfEntity = iamsShelfService.getByAssetId(iamsAccountDto.getAssetId());
			iamsAccountDto.setRole(shelfEntity.getRole());
			return iamsAccountDto;
		}).toList();
		resultPage.setRecords(list);
		return R.ok(resultPage);
    }


    /**
     * 通过id查询账户
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsAccount_view')" )
    public R getById(@PathVariable("id" ) Long id) {
		IamsAccountEntity accountEntity = iamsAccountService.getById(id);
		IamsAccountDto iamsAccountDto = BeanUtil.copyProperties(accountEntity, IamsAccountDto.class);
		IamsAssetEntity assetEntity = iamsAssetService.getById(accountEntity.getAssetId());
		iamsAccountDto.setSn(assetEntity.getSn());
		return R.ok(iamsAccountDto);
    }

    /**
     * 新增账户
     * @param iamsAccount 账户
     * @return R
     */
    @Operation(summary = "新增账户" , description = "新增账户" )
    @SysLog("新增账户" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsAccount_add')" )
    public R save(@RequestBody IamsAccountEntity iamsAccount) {
        return R.ok(iamsAccountService.save(iamsAccount));
    }

    /**
     * 修改账户
     * @param iamsAccount 账户
     * @return R
     */
    @Operation(summary = "修改账户" , description = "修改账户" )
    @SysLog("修改账户" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsAccount_edit')" )
    public R updateById(@RequestBody IamsAccountEntity iamsAccount) {
        return R.ok(iamsAccountService.updateById(iamsAccount));
    }

    /**
     * 通过id删除账户
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除账户" , description = "通过id删除账户" )
    @SysLog("通过id删除账户" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsAccount_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(iamsAccountService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param iamsAccount 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('iams_iamsAccount_export')" )
    public List<IamsAccountEntity> export(IamsAccountEntity iamsAccount,Long[] ids) {
        return iamsAccountService.list(Wrappers.lambdaQuery(iamsAccount).in(ArrayUtil.isNotEmpty(ids), IamsAccountEntity::getId, ids));
    }
}