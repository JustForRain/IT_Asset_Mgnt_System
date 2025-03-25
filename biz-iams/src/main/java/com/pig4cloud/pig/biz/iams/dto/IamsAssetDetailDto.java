package com.pig4cloud.pig.biz.iams.dto;

import com.pig4cloud.pig.biz.iams.entity.IamsAssetEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产详情表单前端展示实体
 *
 * @author pig
 * @date 2025-03-17 16:25:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资产详情表单前端展示实体")
public class IamsAssetDetailDto extends IamsAssetEntity {
}
