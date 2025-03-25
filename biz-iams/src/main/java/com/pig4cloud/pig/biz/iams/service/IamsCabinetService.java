package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsCabinetEntity;

import java.util.List;
import java.util.stream.LongStream;

public interface IamsCabinetService extends IService<IamsCabinetEntity> {

	List<IamsCabinetEntity> listByModuleId(Long moduleId);

	List<IamsCabinetEntity> listByModuleIds(List<Long> ids);
}