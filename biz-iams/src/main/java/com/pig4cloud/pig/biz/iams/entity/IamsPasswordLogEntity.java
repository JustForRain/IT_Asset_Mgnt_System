package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 密码修改记录
 *
 * @author pig
 * @date 2025-03-25 17:35:03
 */
@Data
@TableName("iams_password_log")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "密码修改记录")
public class IamsPasswordLogEntity extends Model<IamsPasswordLogEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 账户ID
	*/
    @Schema(description="账户ID")
    private Long accountId;

	/**
	* 设备ID
	*/
    @Schema(description="设备ID")
    private Long assetId;

	/**
	* 新密码
	*/
    @Schema(description="新密码")
    private String oldPassword;

	/**
	* 旧密码
	*/
    @Schema(description="旧密码")
    private String newPassword;

	/**
	* 创建时间
	*/
	@TableField(fill = FieldFill.INSERT)
    @Schema(description="创建时间")
    private LocalDateTime createTime;
}