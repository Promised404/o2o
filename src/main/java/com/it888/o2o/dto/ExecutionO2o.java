package com.it888.o2o.dto;

import java.util.List;

import com.it888.o2o.enums.EnumO2o;

import lombok.Data;

@Data
public class ExecutionO2o<T> {
	// 结果状态
	private int state;

	// 状态识别
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop（增删改查的时候用到）
	private T t;

	// shop列表（查询店铺列表的时候使用）
	private List<T> list;

	public ExecutionO2o() {
	}

	//成功时的调用构造方法(单个)
	public ExecutionO2o(EnumO2o stateEnum,T t) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.t = t;
	}
	
	//成功时的调用构造方法(集合)
	public ExecutionO2o(EnumO2o stateEnum,List<T> list) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.list = list;
	}

	//返回状态码
	public ExecutionO2o(EnumO2o stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
}
