package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ProductImg {

	private Long productImgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Long productId;

}
