package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsContractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合同前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "合同前端展示实体")
public class IamsContractDto extends IamsContractEntity {

	/**
	 * 资产总数
	 */
	private Integer totalNum;

	/**
	 * 上架资产数
	 */
	private Integer shelfNum;

	/**
	 * 闲置资产数
	 */
	private Integer idleNum;
}
