package com.it888.o2o.util;

/**
 * 分页工具
 * @author 邓鹏涛
 *
 */
public class PageCalculator {
	
	/**
	 * 装换
	 * @param pageIndex 第几页
	 * @param pageSize 每页几条
	 * @return
	 */
	public static int calculateRowIndex(int pageIndex,int pageSize){
		return (pageIndex > 0)?(pageSize - 1) * pageSize : 0;
	}
}
