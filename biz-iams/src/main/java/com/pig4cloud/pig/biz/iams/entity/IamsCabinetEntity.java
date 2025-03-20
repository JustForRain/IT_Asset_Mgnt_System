package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机柜
 *
 * @author pig
 * @date 2025-03-18 09:29:30
 */
@Data
@TableName("iams_cabinet")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机柜")
public class IamsCabinetEntity extends Model<IamsCabinetEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 所属微模块
	*/
    @Schema(description="所属微模块")
    private Long moduleId;

	/**
	* 大小
	*/
    @Schema(description="大小")
    private Integer size;

	/**
	* 所在行
	*/
    @Schema(description="所在行")
    private String rowNum;

	/**
	* 所在列
	*/
    @Schema(description="所在列")
    private String columnNum;

	/**
	* 名称
	*/
    @Schema(description="名称")
    private String name;

	/**
	* 排序值
	*/
    @Schema(description="排序值")
    private Boolean sortOrder;
}