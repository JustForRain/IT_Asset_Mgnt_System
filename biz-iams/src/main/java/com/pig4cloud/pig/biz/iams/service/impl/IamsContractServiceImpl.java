package com.pig4cloud.pig.biz.iams.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsContractEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsContractMapper;
import com.pig4cloud.pig.biz.iams.service.IamsAssetService;
import com.pig4cloud.pig.biz.iams.service.IamsCabinetService;
import com.pig4cloud.pig.biz.iams.service.IamsContractService;
import com.pig4cloud.pig.biz.iams.service.IamsShelfService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Service
@AllArgsConstructor
public class IamsContractServiceImpl extends ServiceImpl<IamsContractMapper, IamsContractEntity> implements IamsContractService {

	private final IamsAssetService iamsAssetService;

	@Override
	public List<IamsAssetEntity> getAssetByProjectName(String projectName) {
		IamsContractEntity iamsContractEntity = getOne(Wrappers.lambdaQuery(IamsContractEntity.class).eq(IamsContractEntity::getProjectName, projectName));
		if (ObjUtil.isNotNull(iamsContractEntity)&&ObjUtil.isNotNull(iamsContractEntity.getId())) {
			return iamsAssetService.listAssetByContractId(iamsContractEntity.getId());
		}
		return List.of();
	}
}