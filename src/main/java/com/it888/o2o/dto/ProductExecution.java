package com.it888.o2o.dto;

import java.util.List;

import com.it888.o2o.entity.Product;
import com.it888.o2o.enums.ProductStateEnum;

import lombok.Data;

@Data
public class ProductExecution {

	//结果状态
	private int state;
	
	//状态识别
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//操作的product（增删改查的时候用到）
	private Product product;
	
	//shop列表（查询店铺列表的时候使用）
	private List<Product> productList;

	public ProductExecution() {
	}

	//只返回状态构造器
	public ProductExecution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	//返回状态并且返回商品信息
	public ProductExecution(ProductStateEnum stateEnum,Product product) {
		this.product = product;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//返回状态并且返回商品列表信息
	public ProductExecution(ProductStateEnum stateEnum,List<Product> productList) {
		this.productList = productList;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	
}
