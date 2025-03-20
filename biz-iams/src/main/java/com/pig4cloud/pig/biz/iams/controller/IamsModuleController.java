package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsModuleDto;
import com.pig4cloud.pig.biz.iams.entity.IamsModuleEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsRoomEntity;
import com.pig4cloud.pig.biz.iams.service.IamsModuleService;
import com.pig4cloud.pig.biz.iams.service.IamsRoomService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微模块
 *
 * @author pig
 * @date 2025-03-18 09:10:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsModule" )
@Tag(description = "iamsModule" , name = "微模块管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsModuleController {

    private final  IamsModuleService iamsModuleService;
	private final IamsRoomService iamsRoomService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsModule 微模块
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsModule_view')" )
    public R getIamsModulePage(@ParameterObject Page page, @ParameterObject IamsModuleEntity iamsModule) {
        LambdaQueryWrapper<IamsModuleEntity> wrapper = Wrappers.lambdaQuery();
		Page searchresult = iamsModuleService.page(page, wrapper);
		List<IamsModuleEntity> records = searchresult.getRecords();
		List list = records.stream().map(module -> {
			IamsModuleDto iamsModuleDto = BeanUtil.copyProperties(module, IamsModuleDto.class);
			IamsRoomEntity iamsRoomEntity = iamsRoomService.getById(iamsModuleDto.getRoomId());
			iamsModuleDto.setRoomName(iamsRoomEntity.getName());
			return iamsModuleDto;
		}).toList();
		searchresult.setRecords(list);
		return R.ok(searchresult);
    }


    /**
     * 通过id查询微模块
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsModule_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(iamsModuleService.getById(id));
    }

    /**
     * 新增微模块
     * @param iamsModule 微模块
     * @return R
     */
    @Operation(summary = "新增微模块" , description = "新增微模块" )
    @SysLog("新增微模块" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsModule_add')" )
    public R save(@RequestBody IamsModuleEntity iamsModule) {
        return R.ok(iamsModuleService.save(iamsModule));
    }

    /**
     * 修改微模块
     * @param iamsModule 微模块
     * @return R
     */
    @Operation(summary = "修改微模块" , description = "修改微模块" )
    @SysLog("修改微模块" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsModule_edit')" )
    public R updateById(@RequestBody IamsModuleEntity iamsModule) {
        return R.ok(iamsModuleService.updateById(iamsModule));
    }

    /**
     * 通过id删除微模块
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除微模块" , description = "通过id删除微模块" )
    @SysLog("通过id删除微模块" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsModule_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(iamsModuleService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param iamsModule 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('iams_iamsModule_export')" )
    public List<IamsModuleEntity> export(IamsModuleEntity iamsModule,Long[] ids) {
        return iamsModuleService.list(Wrappers.lambdaQuery(iamsModule).in(ArrayUtil.isNotEmpty(ids), IamsModuleEntity::getId, ids));
    }
}