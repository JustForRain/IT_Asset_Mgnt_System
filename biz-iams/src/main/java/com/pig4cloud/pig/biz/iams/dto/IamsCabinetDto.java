package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsCabinetEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机柜前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机柜前端展示实体")
public class IamsCabinetDto extends IamsCabinetEntity {

	/**
	* 微模块名称
	*/
    @Schema(description="微模块名称")
    private String moduleName;
}
