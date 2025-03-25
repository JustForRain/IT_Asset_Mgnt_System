package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsModuleEntity;

import java.util.List;

public interface IamsModuleService extends IService<IamsModuleEntity> {

    List<IamsModuleEntity> listByRoomId(Long id);
}