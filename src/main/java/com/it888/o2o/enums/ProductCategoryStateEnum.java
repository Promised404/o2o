package com.it888.o2o.enums;

import lombok.Getter;

/**
 * 商品状态
 * @author 邓鹏涛
 *
 */
@Getter
public enum ProductCategoryStateEnum implements EnumO2o{
	
	DELETE_SUCCESS(2,"操作成功"),
	INSERT_SUCCESS(1,"插入数据成功"),
	NULL_PRODUCT(-1001,"商品状态为空"),
	NULL_LIST(-1002,"商品列表为空"),
	CREATE_FAIL(-1003,"店铺类别创建失败"),
	UPDATE_CATEGORYID_ERRO(-1006,"置空商品类别Id出错"),
	DELETE_FAIL(-1004,"店铺类别删除失败");
	
	private int state;
	private String stateInfo;
	
	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	
	/**
	 * 依据传入的状态值返回enum值
	 * @param state
	 * @return
	 */
	public static ProductCategoryStateEnum stateOf(int state){
		for (ProductCategoryStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}
	
}
