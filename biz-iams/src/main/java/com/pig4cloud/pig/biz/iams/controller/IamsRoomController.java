package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.entity.IamsRoomEntity;
import com.pig4cloud.pig.biz.iams.service.IamsCabinetDetailService;
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
 * 机房
 *
 * @author pig
 * @date 2025-03-18 09:01:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsRoom" )
@Tag(description = "iamsRoom" , name = "机房管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsRoomController {

    private final  IamsRoomService iamsRoomService;
	private final IamsCabinetDetailService iamsCabinetDetailService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsRoom 机房
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_view')" )
    public R getIamsRoomPage(@ParameterObject Page page, @ParameterObject IamsRoomEntity iamsRoom) {
        LambdaQueryWrapper<IamsRoomEntity> wrapper = Wrappers.lambdaQuery();
        return R.ok(iamsRoomService.page(page, wrapper));
    }


    /**
     * 通过id查询机房
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(iamsRoomService.getById(id));
    }

    /**
     * 新增机房
     * @param iamsRoom 机房
     * @return R
     */
    @Operation(summary = "新增机房" , description = "新增机房" )
    @SysLog("新增机房" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_add')" )
    public R save(@RequestBody IamsRoomEntity iamsRoom) {
        return R.ok(iamsRoomService.save(iamsRoom));
    }

    /**
     * 修改机房
     * @param iamsRoom 机房
     * @return R
     */
    @Operation(summary = "修改机房" , description = "修改机房" )
    @SysLog("修改机房" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_edit')" )
    public R updateById(@RequestBody IamsRoomEntity iamsRoom) {
        return R.ok(iamsRoomService.updateById(iamsRoom));
    }

    /**
     * 通过id删除机房
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除机房" , description = "通过id删除机房" )
    @SysLog("通过id删除机房" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(iamsRoomService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param iamsRoom 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_export')" )
    public List<IamsRoomEntity> export(IamsRoomEntity iamsRoom,Long[] ids) {
        return iamsRoomService.list(Wrappers.lambdaQuery(iamsRoom).in(ArrayUtil.isNotEmpty(ids), IamsRoomEntity::getId, ids));
    }
	@GetMapping("/unit/detail/{id}")
    @PreAuthorize("@pms.hasPermission('iams_iamsRoom_view')" )
	public R getUnitDetail(@PathVariable("id") Long id) {
		return R.ok(iamsCabinetDetailService.getUnitDetailByRoomId(id));
	}

}