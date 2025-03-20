package com.pig4cloud.pig.biz.iams.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 合同
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@TableName("iams_contract")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "合同")
public class IamsContractEntity extends Model<IamsContractEntity> {


	/**
	* 主键
	*/
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description="主键")
    private Long id;

	/**
	* 甲方
	*/
    @Schema(description="甲方")
    private String purchaser;

	/**
	* 乙方
	*/
    @Schema(description="乙方")
    private String seller;

	/**
	* 签订日期
	*/
    @Schema(description="签订日期")
    private LocalDate signingTime;

	/**
	* 项目金额
	*/
    @Schema(description="项目金额")
    private BigDecimal price;

	/**
	* 项目名称
	*/
    @Schema(description="项目名称")
    private String projectName;
}