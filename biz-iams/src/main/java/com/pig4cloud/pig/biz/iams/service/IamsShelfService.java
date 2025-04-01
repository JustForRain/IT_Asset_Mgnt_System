package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;

import java.util.List;

public interface IamsShelfService extends IService<IamsShelfEntity> {

    List<IamsShelfEntity> getByCabinetId(Long cabinetId);

	void removeBatchByAssetIds(Long[] assetIds);

	IamsShelfEntity getByAssetId(Long assetId);

	List<IamsShelfEntity> listByAssetIds(List<Long> assetIds);

    List<IamsShelfEntity> listByCabinetId(Long id);
}