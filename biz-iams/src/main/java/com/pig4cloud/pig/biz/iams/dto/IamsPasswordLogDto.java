package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsPasswordLogEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码修改记录前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "密码修改记录前端展示实体")
public class IamsPasswordLogDto extends IamsPasswordLogEntity {

	/**
	* 账号
	*/
    @Schema(description="账号")
    private String account;

	/**
	 * 设备角色
	 */
	private String role;

	/**
	 * 设备序列号
	 */
	private String sn;

}
