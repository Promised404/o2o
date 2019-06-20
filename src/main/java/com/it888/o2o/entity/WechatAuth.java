package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class WechatAuth {
	
	private Long wechatAuthId; 	
	private String openId; 	
	private Date createTime; 	
	private PersonInfo presonInfo; 	

}
