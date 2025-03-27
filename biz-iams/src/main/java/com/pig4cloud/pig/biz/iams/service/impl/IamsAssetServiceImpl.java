package com.pig4cloud.pig.biz.iams.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.*;
import com.pig4cloud.pig.biz.iams.mapper.IamsAssetMapper;
import com.pig4cloud.pig.biz.iams.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产
 *
 * @author pig
 * @date 2025-03-17 16:45:08
 */
@Service
@AllArgsConstructor
public class IamsAssetServiceImpl extends ServiceImpl<IamsAssetMapper, IamsAssetEntity> implements IamsAssetService {

	private final IamsShelfService iamsShelfService;
	private final IamsCabinetService iamsCabinetService;
	private final IamsModuleService iamsModuleService;
	private final IamsRoomService iamsRoomService;

	@Override
	public List<IamsAssetEntity> listAssetByContractId(Long id) {
		LambdaQueryWrapper<IamsAssetEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAssetEntity::getContractId, id);
		return list(queryWrapper);
	}

	@Override
	public IamsAssetEntity getBySn(String sn) {
		LambdaQueryWrapper<IamsAssetEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAssetEntity::getSn, sn);
		return getOne(queryWrapper);
	}

	@Override
	public List<IamsAssetEntity> listBySns(List<String> sns) {
		LambdaQueryWrapper<IamsAssetEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.in(IamsAssetEntity::getSn, sns);
		return list(queryWrapper);
	}

	/**
	 * 根据合同id查询资产总数
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Integer getAssetTotalNumByContractId(Long id) {
		LambdaQueryWrapper<IamsAssetEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAssetEntity::getContractId, id);
		List<IamsAssetEntity> list = list(queryWrapper);
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public List<IamsAssetEntity> listByPutOnShelfEntityList(List<IamsShelfEntity> shelfEntityList) {
		if (!shelfEntityList.isEmpty()) {
			List<Long> assetIds = shelfEntityList.stream().map(shelfEntity -> shelfEntity.getAssetId()).toList();
			LambdaQueryWrapper<IamsAssetEntity> queryWrapper = Wrappers.lambdaQuery();
			queryWrapper.in(IamsAssetEntity::getId, assetIds);
			return list(queryWrapper);
		}
		return List.of();
	}

	@Override
	public void pullOffAssets(Long[] assetIds) {
		LambdaQueryWrapper<IamsAssetEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.in(IamsAssetEntity::getId, assetIds);
		List<IamsAssetEntity> list = list(queryWrapper);
		list.forEach(iamsAssetEntity -> {
			iamsAssetEntity.setStatus(0);
		});
		updateBatchById(list);
	}

	@Override
	public HashMap<String, String> getLocationByAssetId(Long assetId) {
		HashMap<String, String> map = new HashMap<>();
		IamsShelfEntity iamsShelfEntity = iamsShelfService.getByAssetId(assetId);
		// 设备角色
		if (ObjUtil.isNotNull(iamsShelfEntity) && ObjUtil.isNotNull(iamsShelfEntity.getRole())) {
			map.put("role", iamsShelfEntity.getRole());
		}
		if (ObjUtil.isNotNull(iamsShelfEntity) && ObjUtil.isNotNull(iamsShelfEntity.getUnitStart()) && ObjUtil.isNotNull(iamsShelfEntity.getUnitEnd())) {
			// 处理机架信息
			Integer unitStart = iamsShelfEntity.getUnitStart();
			Integer unitEnd = iamsShelfEntity.getUnitEnd();
			//判断1U还是多U设备
			if (ObjUtil.isNotNull(unitStart) && ObjUtil.isNotNull(unitEnd)) {
				if (unitStart.equals(unitEnd)) {
					map.put("shelf", unitStart + "U");
				} else {
					map.put("shelf", unitStart + "U-" + unitEnd + "U");
				}
			}
		}
		// 处理机柜信息
		if (ObjUtil.isNotNull(iamsShelfEntity) && ObjUtil.isNotNull(iamsShelfEntity.getCabinetId())) {
			IamsCabinetEntity iamsCabinetEntity = iamsCabinetService.getById(iamsShelfEntity.getCabinetId());
			map.put("cabinet", iamsCabinetEntity.getName());
			// 处理微模块信息
			if (ObjUtil.isNotNull(iamsCabinetEntity.getModuleId())) {
				IamsModuleEntity iamsModuleEntity = iamsModuleService.getById(iamsCabinetEntity.getModuleId());
				map.put("module", iamsModuleEntity.getName());
				// 处理机房信息
				if (ObjUtil.isNotNull(iamsModuleEntity) && ObjUtil.isNotNull(iamsModuleEntity.getRoomId())) {
					Long roomId = iamsModuleEntity.getRoomId();
					IamsRoomEntity roomEntity = iamsRoomService.getById(roomId);
					if (ObjUtil.isNotNull(roomEntity) && ObjUtil.isNotNull(roomEntity.getName())) {
						map.put("room", roomEntity.getName());
					}
				}
			}
		}
		return map;
	}
}