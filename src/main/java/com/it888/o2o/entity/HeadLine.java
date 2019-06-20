package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class HeadLine {
	
	private Long lineId;
	private String lineName;
	private String lineLink;
	private String lineImg;
	private Integer priority;
	//0.不可用 1.可用
	private Integer enableStatus;
	private Date createTime;
	private Date lastEditTime;

}