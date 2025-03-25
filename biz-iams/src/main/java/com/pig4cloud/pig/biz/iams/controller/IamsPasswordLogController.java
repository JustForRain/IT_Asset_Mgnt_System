package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsPasswordLog 密码修改记录
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsPasswordLog_view')" )
    public R getIamsPasswordLogPage(@ParameterObject Page page, @ParameterObject IamsPasswordLogEntity iamsPasswordLog) {
        LambdaQueryWrapper<IamsPasswordLogEntity> wrapper = Wrappers.lambdaQuery();
        return R.ok(iamsPasswordLogService.page(page, wrapper));
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