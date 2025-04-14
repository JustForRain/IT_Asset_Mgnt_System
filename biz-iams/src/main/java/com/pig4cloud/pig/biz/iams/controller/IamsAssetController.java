package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsAssetDetailDto;
import com.pig4cloud.pig.biz.iams.dto.IamsAssetDto;
import com.pig4cloud.pig.biz.iams.dto.OptionAttributes;
import com.pig4cloud.pig.biz.iams.entity.IamsAccountEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsContractEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.*;
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
import java.util.Objects;

/**
 * 资产
 *
 * @author pig
 * @date 2025-03-17 16:45:08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsAsset" )
@Tag(description = "iamsAsset" , name = "资产管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsAssetController {

    private final  IamsAssetService iamsAssetService;
	private final IamsShelfService iamsShelfService;
	private final IamsContractService iamsContractService;
	private final IamsAccountService iamsAccountService;
	private final IamsAssetAccountService iamsAssetAccountService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsAsset 资产
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_view')" )
    public R getIamsAssetPage(@ParameterObject Page page, @ParameterObject IamsAssetEntity iamsAsset) {
        LambdaQueryWrapper<IamsAssetEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(StrUtil.isNotBlank(iamsAsset.getBrandName()),IamsAssetEntity::getBrandName,iamsAsset.getBrandName());
		wrapper.eq(StrUtil.isNotBlank(iamsAsset.getModel()),IamsAssetEntity::getModel,iamsAsset.getModel());
		wrapper.eq(StrUtil.isNotBlank(iamsAsset.getSn()),IamsAssetEntity::getSn,iamsAsset.getSn());
		wrapper.eq(Objects.nonNull(iamsAsset.getContractId()),IamsAssetEntity::getContractId,iamsAsset.getContractId());
		Page searchresult = iamsAssetService.page(page, wrapper);
		List<IamsAssetEntity> records = searchresult.getRecords();
		List list = records.stream().map(asset -> {
			IamsAssetDto iamsAssetDto = BeanUtil.copyProperties(asset, IamsAssetDto.class);
			IamsContractEntity iamsContractEntity = iamsContractService.getById(asset.getContractId());
			iamsAssetDto.setProject(iamsContractEntity.getProjectName());
			HashMap<String,String> location = iamsAssetService.getLocationByAssetId(asset.getId());
			iamsAssetDto.setRoom(location.get("room"));
			iamsAssetDto.setModule(location.get("module"));
			iamsAssetDto.setCabinet(location.get("cabinet"));
			iamsAssetDto.setShelf(location.get("shelf"));
			iamsAssetDto.setRole(location.get("role"));
			return iamsAssetDto;
		}).toList();
		searchresult.setRecords(list);
		return R.ok(searchresult);
    }


    /**
     * 通过id查询资产
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(iamsAssetService.getById(id));
    }

    /**
     * 新增资产
     * @param iamsAsset 资产
     * @return R
     */
    @Operation(summary = "新增资产" , description = "新增资产" )
    @SysLog("新增资产" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_add')" )
    public R save(@RequestBody IamsAssetEntity iamsAsset) {
        iamsAsset.setStatus(0);
		return R.ok(iamsAssetService.save(iamsAsset));
    }

    /**
     * 修改资产
     * @param iamsAsset 资产
     * @return R
     */
    @Operation(summary = "修改资产" , description = "修改资产" )
    @SysLog("修改资产" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_edit')" )
    public R updateById(@RequestBody IamsAssetEntity iamsAsset) {
        return R.ok(iamsAssetService.updateById(iamsAsset));
    }

    /**
     * 通过id删除资产
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除资产" , description = "通过id删除资产" )
    @SysLog("通过id删除资产" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(iamsAssetService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param iamsAsset 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_export')" )
    public List<IamsAssetEntity> export(IamsAssetEntity iamsAsset,Long[] ids) {
        return iamsAssetService.list(Wrappers.lambdaQuery(iamsAsset).in(ArrayUtil.isNotEmpty(ids), IamsAssetEntity::getId, ids));
    }

     /**
     * 非分页查询
     * @param iamsAsset 资产
     * @return
     */
    @Operation(summary = "非分页查询" , description = "分页查询" )
    @GetMapping("/nopage" )
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_view')" )
    public R getIamsAssetNoPage(@ParameterObject IamsAssetEntity iamsAsset) {
        LambdaQueryWrapper<IamsAssetEntity> wrapper = Wrappers.lambdaQuery();
		//只搜索未上架的资产
		wrapper.eq(IamsAssetEntity::getStatus,iamsAsset.getStatus());
		return R.ok(iamsAssetService.list(wrapper).stream().map(asset->{
			OptionAttributes optionAttributes=new OptionAttributes();
			optionAttributes.setLabel(asset.getSn());
			optionAttributes.setValue(String.valueOf(asset.getId()));
			return optionAttributes;
		}).toList());
    }

	/**
     * 设备详情页面
     * @param id 资产
     * @return
     */
    @Operation(summary = "设备详情页面" , description = "设备详情页面" )
    @GetMapping("/detail/{id}" )
    @PreAuthorize("@pms.hasPermission('iams_iamsAsset_view')" )
    public R getIamsAssetDetail(@PathVariable("id" ) Long id) {
		IamsAssetEntity iamsAssetEntity = iamsAssetService.getById(id);
		IamsAssetDetailDto iamsAssetDetailDto = BeanUtil.copyProperties(iamsAssetEntity, IamsAssetDetailDto.class);
		IamsShelfEntity shelfEntity = iamsShelfService.getByAssetId(id);
		iamsAssetDetailDto.setRole(shelfEntity.getRole());
		List<IamsAccountEntity> iamsAccountEntityList = iamsAccountService.getbyAssetId(id);
		if(iamsAccountEntityList.isEmpty()){
			//单设备查询没有数据的情况下，查询asset_account表
			iamsAssetAccountService.listByAssetId(id).forEach(account->{
				Long accountId = account.getAccountId();
				IamsAccountEntity accountEntity = iamsAccountService.getById(accountId);
				iamsAccountEntityList.add(accountEntity);
			});
		}
		iamsAssetDetailDto.setAccountList(iamsAccountEntityList);
		return R.ok(iamsAssetDetailDto);
    }
}