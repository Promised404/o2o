package com.it888.o2o.dto;

import java.util.List;

import com.it888.o2o.entity.Shop;
import com.it888.o2o.enums.ShopStateEnum;

import lombok.Data;

@Data
public class ShopExecution {
	//结果状态
	private int state;
	
	//状态识别
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//操作的shop（增删改查的时候用到）
	private Shop shop;
	
	//shop列表（查询店铺列表的时候使用）
	private List<Shop> shopList;

	public ShopExecution() {
	}
	
	//商品操作失败时的构造器
	public ShopExecution(ShopStateEnum stateEnum){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//商品操作成功的构造器
	public ShopExecution(ShopStateEnum stateEnum, Shop shop){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	
	//商品操作成功的构造器
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList){
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}
}
