package com.pig4cloud.pig.biz.iams.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 修改密码前端回传实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@Schema(description = "修改密码前端回传实体")
public class ChangePasswordDto {
	private Long id;
	private String oldPassword;
	private String newPassword;
}
