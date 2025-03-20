package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsModuleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微模块前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "微模块前端展示实体")
public class IamsModuleDto extends IamsModuleEntity {
	/**
	* 机房名称
	*/
    @Schema(description="机房名称")
    private String roomName;
}
