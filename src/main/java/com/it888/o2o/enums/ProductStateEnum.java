package com.it888.o2o.enums;

import lombok.Getter;

@Getter
public enum ProductStateEnum implements EnumO2o{
	
	SUCCESS(6,"操作成功"),
	INSERT_FAIL(-1002,"商品插入失败"),
	NULL_PRODUCT(-1001,"product为空或则状态异常"),
	PARAM_ERRO(-1004,"productId有误"),
	PRODUCT_NULL(-1005,"查询商品列表为空"),
	BATCH_INSET_FAIL(-1003,"批量插入图片出错");
	
	
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的状态值返回enum值
	 * @param state
	 * @return
	 */
	public static ProductStateEnum stateOf(int state){
		for (ProductStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}
}
