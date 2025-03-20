package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微模块
 *
 * @author pig
 * @date 2025-03-18 09:10:36
 */
@Data
@TableName("iams_module")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "微模块")
public class IamsModuleEntity extends Model<IamsModuleEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 所属机房
	*/
    @Schema(description="所属机房")
    private Long roomId;

	/**
	* 微模块名称
	*/
    @Schema(description="微模块名称")
    private String name;

	/**
	* 机柜数量
	*/
    @Schema(description="机柜数量")
    private Integer size;
}