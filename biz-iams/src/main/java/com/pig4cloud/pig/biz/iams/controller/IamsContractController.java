package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsContractDto;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsContractEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.IamsAssetService;
import com.pig4cloud.pig.biz.iams.service.IamsContractService;
import com.pig4cloud.pig.biz.iams.service.IamsShelfService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 合同
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsContract" )
@Tag(description = "iamsContract" , name = "合同管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsContractController {

    private final  IamsContractService iamsContractService;
	private final  IamsAssetService iamsAssetService;
	private final IamsShelfService iamsShelfService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param iamsContract 合同
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('pig_iamsContract_view')" )
    public R getIamsContractPage(@ParameterObject Page page, @ParameterObject IamsContractEntity iamsContract) {
        LambdaQueryWrapper<IamsContractEntity> wrapper = Wrappers.lambdaQuery();
		Page resultPage = iamsContractService.page(page, wrapper);
		List<IamsContractEntity> records = resultPage.getRecords();
		resultPage.setRecords(records.stream().map(contract -> {
			IamsContractDto iamsContractDto = BeanUtil.copyProperties(contract, IamsContractDto.class);
			iamsContractDto.setTotalNum(iamsAssetService.getAssetTotalNumByContractId(contract.getId()));
			List<IamsAssetEntity> iamsAssetEntityList = iamsAssetService.listAssetByContractId(iamsContractDto.getId());
			List<IamsShelfEntity> iamsShelfEntityList = iamsShelfService.listByAssetIds(iamsAssetEntityList.stream().map(IamsAssetEntity::getId).toList());
			iamsContractDto.setShelfNum(iamsShelfEntityList.size());
			iamsContractDto.setIdleNum(iamsAssetEntityList.size()-iamsShelfEntityList.size());
			return iamsContractDto;
		}).toList());
		return R.ok(resultPage);
    }


    /**
     * 通过id查询合同
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('pig_iamsContract_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(iamsContractService.getById(id));
    }

    /**
     * 新增合同
     * @param iamsContract 合同
     * @return R
     */
    @Operation(summary = "新增合同" , description = "新增合同" )
    @SysLog("新增合同" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('pig_iamsContract_add')" )
    public R save(@RequestBody IamsContractEntity iamsContract) {
        return R.ok(iamsContractService.save(iamsContract));
    }

    /**
     * 修改合同
     * @param iamsContract 合同
     * @return R
     */
    @Operation(summary = "修改合同" , description = "修改合同" )
    @SysLog("修改合同" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('pig_iamsContract_edit')" )
    public R updateById(@RequestBody IamsContractEntity iamsContract) {
        return R.ok(iamsContractService.updateById(iamsContract));
    }

    /**
     * 通过id删除合同
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除合同" , description = "通过id删除合同" )
    @SysLog("通过id删除合同" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('pig_iamsContract_del')" )
    public R removeById(@RequestBody Long[] ids) {
		ArrayList<Long> delList = new ArrayList<>();
		ArrayList<Long> unDelList = new ArrayList<>();
		for (Long id : ids) {
			Integer totalNum = iamsAssetService.getAssetTotalNumByContractId(id);
			if(totalNum==0){
				delList.add(id);
			}else{
				unDelList.add(id);
			}
		}
		iamsContractService.removeBatchByIds(delList);
		if(!unDelList.isEmpty()){
			return R.failed("所选合同下有资产，部分合同不能删除");
		}
		return R.ok("所选合同删除成功");
    }


    /**
     * 导出excel 表格
     * @param iamsContract 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('pig_iamsContract_export')" )
    public List<IamsContractEntity> export(IamsContractEntity iamsContract,Long[] ids) {
        return iamsContractService.list(Wrappers.lambdaQuery(iamsContract).in(ArrayUtil.isNotEmpty(ids), IamsContractEntity::getId, ids));
    }
}