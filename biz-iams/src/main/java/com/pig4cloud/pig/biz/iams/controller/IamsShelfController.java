package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsShelfDto;
import com.pig4cloud.pig.biz.iams.entity.*;
import com.pig4cloud.pig.biz.iams.service.*;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * 上架
 *
 * @author pig
 * @date 2025-03-18 15:02:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsShelf")
@Tag(description = "iamsShelf", name = "上架管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsShelfController {

	private final IamsShelfService iamsShelfService;
	private final IamsAssetService iamsAssetService;
	private final IamsRoomService iamsRoomService;
	private final IamsModuleService iamsModuleService;
	private final IamsCabinetService iamsCabinetService;
	private final IamsContractService iamsContractService;

	/**
	 * 分页查询
	 *
	 * @param page      分页对象
	 * @param iamsShelf 上架
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')")
	public R getIamsShelfPage(@ParameterObject Page page, @ParameterObject IamsShelfDto iamsShelf) {
		LambdaQueryWrapper<IamsShelfEntity> wrapper = Wrappers.lambdaQuery();
		//存储微模块ID
		List<Long> moduleIdList = new ArrayList<>();
		//根据微模块查到微模块ID
		if (StrUtil.isNotBlank(iamsShelf.getModule())) {
			List<IamsModuleEntity> iamsModuleEntityList = iamsModuleService.list(Wrappers.lambdaQuery(IamsModuleEntity.class).eq(IamsModuleEntity::getName, iamsShelf.getModule()));
			iamsModuleEntityList.forEach(iamsModuleEntity -> {
				if (ObjUtil.isNotNull(iamsModuleEntity) && ObjUtil.isNotNull(iamsModuleEntity.getId())) {
					moduleIdList.add(iamsModuleEntity.getId());
				}
			});

		}
		//根据机柜查询到微模块ID
		if(StrUtil.isNotBlank(iamsShelf.getRoom())){
			//查询机房下的所有微模块和微模块下的所有机柜上架的设备
			List<IamsRoomEntity> iamsRoomEntityList = iamsRoomService.list(Wrappers.lambdaQuery(IamsRoomEntity.class).eq(IamsRoomEntity::getName,iamsShelf.getRoom()));
			iamsRoomEntityList.forEach(iamsRoomEntity -> {
				if(ObjUtil.isNotNull(iamsRoomEntity)&&ObjUtil.isNotNull(iamsRoomEntity.getId())){
					List<IamsModuleEntity> moduleEntityList = iamsModuleService.listByRoomId(iamsRoomEntity.getId());
					if (CollUtil.isNotEmpty(moduleEntityList)) {
						List<Long> list = moduleEntityList.stream().mapToLong(IamsModuleEntity::getId).boxed().toList();
						moduleIdList.addAll(list);
					}
				}
			});
		}
		//存储机柜ID
		List<Long> cabinetIdList = new ArrayList<>();
		if(!moduleIdList.isEmpty()){
			List<IamsCabinetEntity> cabinetEntityList = iamsCabinetService.listByModuleIds(moduleIdList);
			if(!cabinetEntityList.isEmpty()){
				List<Long> list = cabinetEntityList.stream().mapToLong(IamsCabinetEntity::getId).boxed().toList();
				cabinetIdList.addAll(list);
			}
		}
		if (StrUtil.isNotBlank(iamsShelf.getCabinet())) {
			List<IamsCabinetEntity> cabinetEntityList = iamsCabinetService.list(Wrappers.lambdaQuery(IamsCabinetEntity.class).eq(IamsCabinetEntity::getName, iamsShelf.getCabinet()));
			List<Long> list = cabinetEntityList.stream().map(IamsCabinetEntity::getId).toList();
			cabinetIdList.addAll(list);
		}
		wrapper.in(CollUtil.isNotEmpty(cabinetIdList), IamsShelfEntity::getCabinetId, cabinetIdList);
		Page resultPage = iamsShelfService.page(page, wrapper);
		List<IamsShelfEntity> records = resultPage.getRecords();
		List<IamsShelfDto> list = records.stream().map(iamsShelfEntity -> {
			IamsShelfDto iamsShelfDto = BeanUtil.copyProperties(iamsShelfEntity, IamsShelfDto.class);
			HashMap<String, String> location = iamsAssetService.getLocationByAssetId(iamsShelfEntity.getAssetId());
			iamsShelfDto.setRoom(location.get("room"));
			iamsShelfDto.setModule(location.get("module"));
			iamsShelfDto.setCabinet(location.get("cabinet"));
			iamsShelfDto.setShelf(location.get("shelf"));
			iamsShelfDto.setRole(location.get("role"));
			return iamsShelfDto;
		}).toList();
		//根据项目查询机柜
		if (StrUtil.isNotBlank(iamsShelf.getProjectName())) {
			List<IamsAssetEntity> assetByProjectName = iamsContractService.getAssetByProjectName(iamsShelf.getProjectName());
			if (ObjUtil.isNotNull(assetByProjectName) && !assetByProjectName.isEmpty()) {
				Set<Long> assetIdSet = assetByProjectName.stream().map(IamsAssetEntity::getId).collect(Collectors.toSet());
				list = list.stream().filter(iamsShelfDto -> assetIdSet.contains(iamsShelfDto.getAssetId())).collect(Collectors.toList());
			}
		}
		resultPage.setRecords(list);
		return R.ok(resultPage);
	}


	/**
	 * 通过id查询上架
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(iamsShelfService.getById(id));
	}

	/**
	 * 新增上架
	 *
	 * @param iamsShelf 上架
	 * @return R
	 */
	@Operation(summary = "新增上架", description = "新增上架")
	@SysLog("新增上架")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_add')")
	public R save(@RequestBody IamsShelfEntity iamsShelf) {
		IamsAssetEntity iamsAssetEntity = iamsAssetService.getById(iamsShelf.getAssetId());
		iamsAssetEntity.setStatus(1);
		if (iamsShelf.getUnitStart() > iamsShelf.getUnitEnd()) {
			Integer unitStart = iamsShelf.getUnitStart();
			iamsShelf.setUnitStart(iamsShelf.getUnitEnd());
			iamsShelf.setUnitEnd(unitStart);
		}
		iamsAssetService.updateById(iamsAssetEntity);
		return R.ok(iamsShelfService.save(iamsShelf));
	}

	/**
	 * 修改上架
	 *
	 * @param iamsShelf 上架
	 * @return R
	 */
	@Operation(summary = "修改上架", description = "修改上架")
	@SysLog("修改上架")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_edit')")
	public R updateById(@RequestBody IamsShelfEntity iamsShelf) {
		return R.ok(iamsShelfService.updateById(iamsShelf));
	}

	/**
	 * 通过id删除上架
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除上架", description = "通过id删除上架")
	@SysLog("通过id删除上架")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(iamsShelfService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param iamsShelf 查询条件
	 * @param ids       导出指定ID
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_export')")
	public List<IamsShelfEntity> export(IamsShelfEntity iamsShelf, Long[] ids) {
		return iamsShelfService.list(Wrappers.lambdaQuery(iamsShelf).in(ArrayUtil.isNotEmpty(ids), IamsShelfEntity::getId, ids));
	}

	/**
	 * 通过id查询已上架资产
	 *
	 * @param cabinetId 机柜id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/puton/{cabinetId}")
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')")
	public R puton(@PathVariable("cabinetId") Long cabinetId) {
		List<IamsShelfEntity> shelfEntityList = iamsShelfService.getByCabinetId(cabinetId);
		List<IamsAssetEntity> iamsAssetEntities = iamsAssetService.listByPutOnShelfEntityList(shelfEntityList);
		return R.ok(iamsAssetEntities.stream().map(iamsAssetEntity -> {
			SelectOption selectOption = new SelectOption();
			selectOption.setLabel(iamsAssetEntity.getSn());
			selectOption.setKey(String.valueOf(iamsAssetEntity.getId()));
			return selectOption;
		}).toList());
	}

	/**
	 * 下架资产
	 *
	 * @param assetIds 设备ID列表
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@DeleteMapping("/pulloff")
	@PreAuthorize("@pms.hasPermission('iams_iamsShelf_view')")
	public R pulloff(@RequestBody Long[] assetIds) {
		//删除上架记录
		iamsShelfService.removeBatchByAssetIds(assetIds);
		//修改资产状态
		iamsAssetService.pullOffAssets(assetIds);
		return R.ok();
	}
}