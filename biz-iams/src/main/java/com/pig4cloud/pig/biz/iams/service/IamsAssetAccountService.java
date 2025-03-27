package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetAccountEntity;

import java.util.List;

public interface IamsAssetAccountService extends IService<IamsAssetAccountEntity> {

	List<IamsAssetAccountEntity> listByAccountId(Long id);

	Boolean removeByAccountId(Long id);

	List<IamsAssetAccountEntity> listByAssetId(Long id);
}