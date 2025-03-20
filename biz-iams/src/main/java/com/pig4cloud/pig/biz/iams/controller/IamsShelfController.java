package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsShelfDto;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.IamsAssetService;
import com.pig4cloud.pig.biz.iams.service.IamsShelfService;
import com.pig4cloud.pig.biz.iams.vo.SelectOption;
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

import java.util.HashMap;
import java.util.List;

/**
 * 上架
 *
 * @author pig
 * @date 2025-03-18 15:02:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsShelf" )
@Tag(description = "iamsShelf" , name = "上架管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsShelfController {

    private final  IamsShelfService iamsShelfService;
	private final IamsAssetService iamsAssetService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsShelf 上架
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')" )
    public R getIamsShelfPage(@ParameterObject Page page, @ParameterObject IamsShelfEntity iamsShelf) {
        LambdaQueryWrapper<IamsShelfEntity> wrapper = Wrappers.lambdaQuery();
		Page resultPage = iamsShelfService.page(page, wrapper);
		List<IamsShelfEntity> records = resultPage.getRecords();
		List<IamsShelfDto> list = records.stream().map(iamsShelfEntity -> {
			IamsShelfDto iamsShelfDto = BeanUtil.copyProperties(iamsShelfEntity, IamsShelfDto.class);
			HashMap<String,String> location = iamsAssetService.getLocationByAssetId(iamsShelfEntity.getAssetId());
			iamsShelfDto.setRoomName(location.get("roomName"));
			iamsShelfDto.setModuleName(location.get("moduleName"));
			iamsShelfDto.setCabinetName(location.get("cabinetName"));
			iamsShelfDto.setShelfName(location.get("shelfName"));
			iamsShelfDto.setRoleName(location.get("roleName"));
			return iamsShelfDto;
		}).toList();
		resultPage.setRecords(list);
		return R.ok(resultPage);
    }


    /**
     * 通过id查询上架
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(iamsShelfService.getById(id));
    }

    /**
     * 新增上架
     * @param iamsShelf 上架
     * @return R
     */
    @Operation(summary = "新增上架" , description = "新增上架" )
    @SysLog("新增上架" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_add')" )
    public R save(@RequestBody IamsShelfEntity iamsShelf) {
		IamsAssetEntity iamsAssetEntity = iamsAssetService.getById(iamsShelf.getAssetId());
		iamsAssetEntity.setStatus(1);
		if(iamsShelf.getUnitStart()>iamsShelf.getUnitEnd()){
			Integer unitStart = iamsShelf.getUnitStart();
			iamsShelf.setUnitStart(iamsShelf.getUnitEnd());
			iamsShelf.setUnitEnd(unitStart);
		}
		iamsAssetService.updateById(iamsAssetEntity);
		return R.ok(iamsShelfService.save(iamsShelf));
    }

    /**
     * 修改上架
     * @param iamsShelf 上架
     * @return R
     */
    @Operation(summary = "修改上架" , description = "修改上架" )
    @SysLog("修改上架" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_edit')" )
    public R updateById(@RequestBody IamsShelfEntity iamsShelf) {
        return R.ok(iamsShelfService.updateById(iamsShelf));
    }

    /**
     * 通过id删除上架
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除上架" , description = "通过id删除上架" )
    @SysLog("通过id删除上架" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(iamsShelfService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param iamsShelf 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_export')" )
    public List<IamsShelfEntity> export(IamsShelfEntity iamsShelf,Long[] ids) {
        return iamsShelfService.list(Wrappers.lambdaQuery(iamsShelf).in(ArrayUtil.isNotEmpty(ids), IamsShelfEntity::getId, ids));
    }

    /**
     * 通过id查询已上架资产
     * @param cabinetId 机柜id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/puton/{cabinetId}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')" )
    public R puton(@PathVariable("cabinetId" ) Long cabinetId) {
		List<IamsShelfEntity> shelfEntityList = iamsShelfService.getByCabinetId(cabinetId);
		List<IamsAssetEntity> iamsAssetEntities = iamsAssetService.listByPutOnShelfEntityList(shelfEntityList);
		return R.ok(iamsAssetEntities.stream().map(iamsAssetEntity -> {
			SelectOption selectOption=new SelectOption();
			selectOption.setLabel(iamsAssetEntity.getSn());
			selectOption.setKey(String.valueOf(iamsAssetEntity.getId()));
			return selectOption;
		}).toList());
    }

    /**
     * 下架资产
     * @param assetIds 设备ID列表
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @DeleteMapping("/pulloff" )
    @PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')" )
    public R pulloff(@RequestBody Long[] assetIds) {
		//删除上架记录
		iamsShelfService.removeBatchByAssetIds(assetIds);
		//修改资产状态
		iamsAssetService.pullOffAssets(assetIds);
		return R.ok();
    }
}