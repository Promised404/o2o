package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Area {
	
	//ID
	private Integer areaId;
	//名称
	private String areaName;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	//更新时间
	private Date lastEditTime;
	
}
