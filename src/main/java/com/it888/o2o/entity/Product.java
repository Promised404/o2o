package com.it888.o2o.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Product {
	
	private Long productId;
	private String productName;
	private String productDesc;
	private String imgAddr;// 简略图
	private String normalPrice;
	private String promotionPrice;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	//0.下架 1.在前端展示系统展示
	private Integer enableStatus;

	private List<ProductImg> productImgList;
	private ProductCategory productCategory;
	private Shop shop;
	
}