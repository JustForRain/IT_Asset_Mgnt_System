package com.pig4cloud.pig.biz.iams.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class IamsRackDetail {
	private String cabinetName;
	private ArrayList<IamsUnitDetail> unitDetails;
}
