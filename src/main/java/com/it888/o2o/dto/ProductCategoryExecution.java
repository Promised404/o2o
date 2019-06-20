package com.it888.o2o.dto;

import java.util.List;

import com.it888.o2o.entity.ProductCategory;
import com.it888.o2o.enums.ProductCategoryStateEnum;

import lombok.Data;

/**
 * 商品分类dto
 * @author 邓鹏涛
 *
 */
@Data
public class ProductCategoryExecution {
	// 结果状态
	private int state;

	// 状态识别
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop（增删改查的时候用到）
	private ProductCategory productCategory;

	// shop列表（查询店铺列表的时候使用）
	private List<ProductCategory> productCategoryList;

	public ProductCategoryExecution() {
	}

	//成功时的调用构造方法(单个)
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,ProductCategory productCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategory = productCategory;
	}
	
	//成功时的调用构造方法(集合)
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
	}

	//返回状态码
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	
}
