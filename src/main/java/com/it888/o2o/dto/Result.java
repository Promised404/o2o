package com.it888.o2o.dto;

import lombok.Data;

/**
 * 封装json对象，所有的返回结果都使用它
 */
@Data
public class Result<T> {

	//是否成功标志
	private boolean success;
	
	//成功时返回的数据
	private T data;
	
	//错误信息
	private String errorMsg;
	
	//错误码
	private int errorCode;

	public Result() {
	}
	
	//成功时的构造器
	public Result(boolean succuss,T data){
		this.success = succuss;
		this.data = data;
	}

	//错误时的构造器
	public Result(boolean success, String errorMsg, int errorCode) {
		this.success = success;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}

	public boolean isSuccess(){
		return success;
	}
	
}
