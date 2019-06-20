package com.it888.o2o.enums;

import lombok.Getter;

/**
 * 店铺状态
 * @author 邓鹏涛
 *
 */
@Getter
public enum ShopStateEnum implements EnumO2o {
	CHCKE(0,"审核中"),
	OFFLINE(-1,"非法店铺"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"ShopId为空");
	
	private int state;
	private String stateInfo;
	
	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的状态值返回enum值
	 * @param state
	 * @return
	 */
	public static ShopStateEnum stateOf(int state){
		for (ShopStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}
	
	
}
