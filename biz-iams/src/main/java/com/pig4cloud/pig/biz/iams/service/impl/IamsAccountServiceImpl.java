package com.pig4cloud.pig.biz.iams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsAccountEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsAccountMapper;
import com.pig4cloud.pig.biz.iams.service.IamsAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户
 *
 * @author pig
 * @date 2025-03-25 16:00:21
 */
@Service
public class IamsAccountServiceImpl extends ServiceImpl<IamsAccountMapper, IamsAccountEntity> implements IamsAccountService {
	@Override
	public List<IamsAccountEntity> getbyAssetId(Long id) {
		LambdaQueryWrapper<IamsAccountEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(IamsAccountEntity::getAssetId, id);
		return list(queryWrapper);
	}

}