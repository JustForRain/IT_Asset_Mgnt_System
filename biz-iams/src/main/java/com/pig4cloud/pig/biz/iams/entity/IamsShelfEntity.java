package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上架
 *
 * @author pig
 * @date 2025-03-18 15:02:03
 */
@Data
@TableName("iams_shelf")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "上架")
public class IamsShelfEntity extends Model<IamsShelfEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 机柜编号
	*/
    @Schema(description="机柜编号")
    private Long cabinetId;

	/**
	* 资产编号
	*/
    @Schema(description="资产编号")
    private Long assetId;

	/**
	* 占用unit起始位置
	*/
    @Schema(description="占用unit起始位置")
    private Integer unitStart;

	/**
	* 占用unit结束位置
	*/
    @Schema(description="占用unit结束位置")
    private Integer unitEnd;

	/**
	* 角色
	*/
    @Schema(description="角色")
    private String role;
}