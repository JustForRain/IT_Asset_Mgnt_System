package com.pig4cloud.pig.biz.iams.dto;

import lombok.Data;

@Data
public class OptionAttributes {
	/**
	 * 选项的值
	 */
	private String value;
	/**
	 * 选项的标签，若不设置则默认与value相同
	 */
	private String label;
	/**
	 * 是否禁用该选项
	 * 默认false
	 */
	private Boolean disabled;
}
