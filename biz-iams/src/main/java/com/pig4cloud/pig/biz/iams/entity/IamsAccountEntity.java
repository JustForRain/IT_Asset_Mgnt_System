package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户
 *
 * @author pig
 * @date 2025-03-25 16:00:21
 */
@Data
@TableName("iams_account")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "账户")
public class IamsAccountEntity extends Model<IamsAccountEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 设备主键ID
	*/
    @Schema(description="设备主键ID")
    private Long assetId;

	/**
	* 地址
	*/
    @Schema(description="地址")
    private String url;

	/**
	* 类型
	*/
    @Schema(description="类型")
    private String type;

	/**
	* 账号
	*/
    @Schema(description="账号")
    private String account;

	/**
	* 密码
	*/
    @Schema(description="密码")
    private String password;

	/**
	* 协议
	*/
    @Schema(description="协议")
    private String protocol;

	/**
	* 端口
	*/
    @Schema(description="端口")
    private Integer port;

	/**
	* MAC地址
	*/
    @Schema(description="MAC地址")
    private String macAddress;

	/**
	* 单一设备
	*/
    @Schema(description="单一设备")
    private Boolean single;

	/**
	* 虚拟SN
	*/
    @Schema(description="虚拟SN")
    private String vsn;

}