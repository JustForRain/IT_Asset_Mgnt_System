package com.pig4cloud.pig.biz.iams.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.pig4cloud.pig.biz.iams.dto.IamsRackDetail;
import com.pig4cloud.pig.biz.iams.dto.IamsShelfDto;
import com.pig4cloud.pig.biz.iams.dto.IamsUnitDetail;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsCabinetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsModuleEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yu.xia
 */
@Service
@AllArgsConstructor
public class IamsCabinetDetailServiceImpl implements IamsCabinetDetailService {

	private final IamsCabinetService iamsCabinetService;
	private final IamsShelfService iamsShelfService;
	private final IamsAssetService iamsAssetService;
	private final IamsModuleService iamsModuleService;

	@Override
	public IamsRackDetail getUnitDetail(Long id) {
		IamsRackDetail iamsRackDetail = new IamsRackDetail();
		IamsCabinetEntity iamsCabinetEntity = iamsCabinetService.getById(id);
		iamsRackDetail.setCabinetName(iamsCabinetEntity.getName());
		//获取已上架的设备
		List<IamsShelfEntity> shelfEntityList = iamsShelfService.getByCabinetId(id);
		ArrayList<IamsUnitDetail> unitDetails = new ArrayList<>();
		for (int i = 1; i <= iamsCabinetEntity.getSize(); i++) {
			IamsUnitDetail iamsUnitDetail = new IamsUnitDetail();
			iamsUnitDetail.setUnitNum(i);
			unitDetails.add(iamsUnitDetail);
			int finalI = i;
			shelfEntityList.forEach(iamsShelfEntity -> {
				if (finalI >= iamsShelfEntity.getUnitStart() && finalI <= iamsShelfEntity.getUnitEnd()) {
					IamsAssetEntity iamsAssetEntity = iamsAssetService.getById(iamsShelfEntity.getAssetId());
					IamsShelfDto iamsShelfDto = BeanUtil.copyProperties(iamsAssetEntity, IamsShelfDto.class);
					iamsShelfDto.setSize(iamsAssetEntity.getSize());
					iamsShelfDto.setRole(iamsShelfEntity.getRole());
					iamsUnitDetail.setDevice(iamsShelfDto);
				}
			});
		}
		//判断是否需要排序
		if (iamsCabinetEntity.getSortOrder()) {
			//逆序
			Collections.reverse(unitDetails);
		}
		iamsRackDetail.setUnitDetails(unitDetails);
		return iamsRackDetail;
	}

	@Override
	public List<IamsCabinetEntity> getUnitDetailByRoomId(Long id) {
		List<IamsModuleEntity> moduleEntityList = iamsModuleService.listByRoomId(id);
		List<Long> list = moduleEntityList.stream().mapToLong(IamsModuleEntity::getId).boxed().toList();
		return iamsCabinetService.listByModuleIds(list);
	}

	@Override
	public List<IamsCabinetEntity> getUnitDetailByModuleId(Long moduleId) {
		return iamsCabinetService.listByModuleId(moduleId);
	}
}
