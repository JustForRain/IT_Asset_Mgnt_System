package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机柜上架设备前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机柜上架设备前端展示实体")
public class IamsShelfDto extends IamsShelfEntity {
	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 序列号
	 */
	private String sn;

	/**
	 * 机房名称
	 */
	private String room;

	/**
	 * 微模块名称
	 */
	private String module;

	/**
	 * 机柜名称
	 */
	private String cabinet;

	/**
	 * 上架位置
	 */
	private String shelf;

	/**
	 * 设备大小
	 */
	private Integer size;
}
