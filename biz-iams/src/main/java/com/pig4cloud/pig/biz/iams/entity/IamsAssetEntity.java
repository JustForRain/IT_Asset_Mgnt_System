package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产
 *
 * @author pig
 * @date 2025-03-17 16:45:08
 */
@Data
@TableName("iams_asset")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资产")
public class IamsAssetEntity extends Model<IamsAssetEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 品牌
	*/
    @Schema(description="品牌")
    private String brandName;

	/**
	* 型号
	*/
    @Schema(description="型号")
    private String model;

	/**
	* 序列号
	*/
    @Schema(description="序列号")
    private String sn;

	/**
	* 设备占用机柜大小
	*/
    @Schema(description="设备占用机柜大小")
    private Integer size;

	/**
	* 关联合同ID
	*/
    @Schema(description="关联合同ID")
    private Long contractId;

	/**
	* 设备状态：0未上架1已上架
	*/
    @Schema(description="设备状态：0未上架1已上架")
    private Integer status;

	/**
	 * 设备类型
	 */
	private String type;
}