package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsContractEntity;

import java.util.List;

public interface IamsContractService extends IService<IamsContractEntity> {

    List<IamsAssetEntity> getAssetByProjectName(String projectName);
}