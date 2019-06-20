package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ShopCategory {

	private Long shopCategoryId;
	private String shopCategoryName;
	private String shopCategoryDesc;
	private String shopCategoryImg;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private ShopCategory parent;
	
}
