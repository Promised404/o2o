package com.it888.o2o.entity;

import java.util.Date;

import lombok.Data;

@Data
public class PersonInfo {
	
	private Long userId;
	private String name;
	private String profileImg;
	private String email;
	private String gender;
	private Integer enableStatus;
	//1.顾客   2.店家  3.超级管理员
	private Integer userType;
	private Date createTime;
	private Date lastEditTime;

}
