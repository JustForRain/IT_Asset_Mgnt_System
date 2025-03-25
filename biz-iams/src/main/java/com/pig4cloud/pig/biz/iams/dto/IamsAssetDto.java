package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资产前端展示实体")
public class IamsAssetDto extends IamsAssetEntity {

	/**
	* 项目名称
	*/
    @Schema(description="项目名称")
    private String project;

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
	 * 设备角色
	 */
	private String role;

}
