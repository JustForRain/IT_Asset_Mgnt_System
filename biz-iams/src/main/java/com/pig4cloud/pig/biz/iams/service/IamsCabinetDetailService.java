package com.pig4cloud.pig.biz.iams.service;

import com.pig4cloud.pig.biz.iams.dto.IamsRackDetail;
import com.pig4cloud.pig.biz.iams.entity.IamsCabinetEntity;

import java.util.List;

public interface IamsCabinetDetailService {
	IamsRackDetail getUnitDetail(Long id);

	List<IamsCabinetEntity> getUnitDetailByModuleId(Long moduleId);

	List<IamsCabinetEntity> getUnitDetailByRoomId(Long id);
}
