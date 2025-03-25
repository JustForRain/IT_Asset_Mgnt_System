package com.pig4cloud.pig.biz.iams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;

import java.util.HashMap;
import java.util.List;

public interface IamsAssetService extends IService<IamsAssetEntity> {

	/**
	 * 根据合同id查询资产总数
	 * @param id
	 * @return
	 */
	Integer getAssetTotalNumByContractId(Long id);

    List<IamsAssetEntity> listByPutOnShelfEntityList(List<IamsShelfEntity> shelfEntityList);

	void pullOffAssets(Long[] assetIds);

	HashMap<String, String> getLocationByAssetId(Long assetId);

	List<IamsAssetEntity> listAssetByContractId(Long id);

	IamsAssetEntity getBySn(String sn);
}