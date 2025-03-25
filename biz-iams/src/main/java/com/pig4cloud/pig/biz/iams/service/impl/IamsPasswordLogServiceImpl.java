package com.pig4cloud.pig.biz.iams.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.biz.iams.entity.IamsPasswordLogEntity;
import com.pig4cloud.pig.biz.iams.mapper.IamsPasswordLogMapper;
import com.pig4cloud.pig.biz.iams.service.IamsPasswordLogService;
import org.springframework.stereotype.Service;
/**
 * 密码修改记录
 *
 * @author pig
 * @date 2025-03-25 17:35:03
 */
@Service
public class IamsPasswordLogServiceImpl extends ServiceImpl<IamsPasswordLogMapper, IamsPasswordLogEntity> implements IamsPasswordLogService {
}