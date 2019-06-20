package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class LocalAuth {
	
	private Long localAuthId;
	private String username;
	private String password;
	private Date createTime;
	private Date lastEditTime;
	private PersonInfo personInfo;
	
}
