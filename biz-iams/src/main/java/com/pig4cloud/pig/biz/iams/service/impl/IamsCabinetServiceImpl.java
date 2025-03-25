package com.pig4cloud.pig.biz.iams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsCabinetEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsCabinetMapper;
import com.pig4cloud.pig.biz.iams.service.IamsCabinetService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机柜
 *
 * @author pig
 * @date 2025-03-18 09:29:30
 */
@Service
public class IamsCabinetServiceImpl extends ServiceImpl<IamsCabinetMapper, IamsCabinetEntity> implements IamsCabinetService {
	@Override
	public List<IamsCabinetEntity> listByModuleId(Long moduleId) {
		LambdaQueryWrapper<IamsCabinetEntity> wrapper= Wrappers.lambdaQuery();
		wrapper.eq(IamsCabinetEntity::getModuleId,moduleId);
		return list(wrapper);
	}

	@Override
	public List<IamsCabinetEntity> listByModuleIds(List<Long> ids) {
		LambdaQueryWrapper<IamsCabinetEntity> wrapper= Wrappers.lambdaQuery();
		wrapper.in(!ids.isEmpty(),IamsCabinetEntity::getModuleId, ids);
		return list(wrapper);
	}
}