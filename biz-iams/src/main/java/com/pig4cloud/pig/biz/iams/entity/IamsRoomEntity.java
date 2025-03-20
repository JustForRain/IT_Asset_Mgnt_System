package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机房
 *
 * @author pig
 * @date 2025-03-18 09:01:37
 */
@Data
@TableName("iams_room")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "机房")
public class IamsRoomEntity extends Model<IamsRoomEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 名称
	*/
    @Schema(description="名称")
    private String name;

	/**
	* 地址
	*/
    @Schema(description="地址")
    private String location;

	/**
	* 管理单位
	*/
    @Schema(description="管理单位")
    private String manager;
}