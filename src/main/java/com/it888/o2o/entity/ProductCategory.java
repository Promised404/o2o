package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ProductCategory {

	private Long productCategoryId;
	private Long shopId;
	private String productCategoryName;
	private Integer priority;
	private Date createTime;

}
