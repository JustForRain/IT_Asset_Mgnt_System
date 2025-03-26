package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsAccountEntity;

import java.util.List;

public interface IamsAccountService extends IService<IamsAccountEntity> {

    List<IamsAccountEntity> getbyAssetId(Long id);

	boolean updateByVsn(IamsAccountEntity iamsAccount);
}