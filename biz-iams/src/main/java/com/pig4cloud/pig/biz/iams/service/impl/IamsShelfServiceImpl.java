package com.pig4cloud.pig.biz.iams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsShelfMapper;
import com.pig4cloud.pig.biz.iams.service.IamsShelfService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 上架
 *
 * @author pig
 * @date 2025-03-18 15:02:03
 */
@Service
public class IamsShelfServiceImpl extends ServiceImpl<IamsShelfMapper, IamsShelfEntity> implements IamsShelfService {
	@Override
	public List<IamsShelfEntity> getByCabinetId(Long cabinetId) {
		LambdaQueryWrapper<IamsShelfEntity> wrapper= Wrappers.lambdaQuery();
		wrapper.eq(IamsShelfEntity::getCabinetId,cabinetId);
		return list(wrapper);
	}

	@Override
	public void removeBatchByAssetIds(Long[] assetIds) {
		LambdaQueryWrapper<IamsShelfEntity> wrapper= Wrappers.lambdaQuery();
		wrapper.in(IamsShelfEntity::getAssetId,assetIds);
		remove(wrapper);
	}

	@Override
	public IamsShelfEntity getByAssetId(Long assetId) {
		LambdaQueryWrapper<IamsShelfEntity> wrapper= Wrappers.lambdaQuery();
		wrapper.eq(IamsShelfEntity::getAssetId,assetId);
		return getOne(wrapper);
	}

	@Override
	public List<IamsShelfEntity> listByCabinetId(Long id) {
		LambdaQueryWrapper<IamsShelfEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(IamsShelfEntity::getCabinetId,id);
		return list(wrapper);
	}

	@Override
	public List<IamsShelfEntity> listByAssetIds(List<Long> assetIds) {
		LambdaQueryWrapper<IamsShelfEntity> wrapper= Wrappers.lambdaQuery();
		wrapper.in(!assetIds.isEmpty(),IamsShelfEntity::getAssetId,assetIds);
		return list(wrapper);
	}

}