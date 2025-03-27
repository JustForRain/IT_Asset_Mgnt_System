package com.pig4cloud.pig.biz.iams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetAccountEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsAssetAccountMapper;
import com.pig4cloud.pig.biz.iams.service.IamsAssetAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备_账户关联表
 *
 * @author pig
 * @date 2025-03-26 15:54:06
 */
@Service
public class IamsAssetAccountServiceImpl extends ServiceImpl<IamsAssetAccountMapper, IamsAssetAccountEntity> implements IamsAssetAccountService {
	@Override
	public List<IamsAssetAccountEntity> listByAccountId(Long id) {
		LambdaQueryWrapper<IamsAssetAccountEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAssetAccountEntity::getAccountId, id);
		return list(queryWrapper);
	}

	@Override
	public Boolean removeByAccountId(Long id) {
		LambdaQueryWrapper<IamsAssetAccountEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAssetAccountEntity::getAccountId, id);
		return remove(queryWrapper);
	}

	@Override
	public List<IamsAssetAccountEntity> listByAssetId(Long id) {
		LambdaQueryWrapper<IamsAssetAccountEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAssetAccountEntity::getAssetId, id);
		return list(queryWrapper);
	}
}