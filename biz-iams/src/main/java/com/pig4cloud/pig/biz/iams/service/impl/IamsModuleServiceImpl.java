package com.pig4cloud.pig.biz.iams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsModuleEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsModuleMapper;
import com.pig4cloud.pig.biz.iams.service.IamsModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微模块
 *
 * @author pig
 * @date 2025-03-18 09:10:36
 */
@Service
public class IamsModuleServiceImpl extends ServiceImpl<IamsModuleMapper, IamsModuleEntity> implements IamsModuleService {
	@Override
	public List<IamsModuleEntity> listByRoomId(Long id) {
		LambdaQueryWrapper<IamsModuleEntity> queryWrapper= Wrappers.lambdaQuery();
		queryWrapper.eq(IamsModuleEntity::getRoomId,id);
		return list(queryWrapper);
	}
}