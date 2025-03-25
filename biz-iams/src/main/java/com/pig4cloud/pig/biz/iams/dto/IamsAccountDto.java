package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsAccountEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "账户前端展示实体")
public class IamsAccountDto extends IamsAccountEntity {
	/**
	 * 设备角色
	 */
	private String role;

	/**
	 * 设备SN
	 */
	private String sn;
}
