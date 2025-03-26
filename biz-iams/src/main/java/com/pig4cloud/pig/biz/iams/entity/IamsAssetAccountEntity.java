package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备_账户关联表
 *
 * @author pig
 * @date 2025-03-26 15:54:06
 */
@Data
@TableName("iams_asset_account")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "设备_账户关联表")
@Builder
public class IamsAssetAccountEntity extends Model<IamsAssetAccountEntity> {

 
	/**
	* assetId
	*/
    @Schema(description="assetId")
    private Long assetId;
 
	/**
	* accountId
	*/
    @Schema(description="accountId")
    private Long accountId;
}