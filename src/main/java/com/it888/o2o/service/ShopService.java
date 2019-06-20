package com.it888.o2o.service;




import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.it888.o2o.dto.ShopExecution;
import com.it888.o2o.entity.Shop;

public interface ShopService {
	
	/**
	 * 注册店铺信息，包括图片
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	ShopExecution addShop(Shop shop,CommonsMultipartFile shopImg);
	
	/**
	 * 通过店铺id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	
	/**
	 * 更新商铺信息，包括对图片的处理
	 * @param shop
	 * @param shopImg
	 * @param fileName
	 * @return
	 */
	ShopExecution modifyShop(Shop shop,CommonsMultipartFile shopImg);
	
	/**
	 * 分页查询店铺列表
	 * @param shopCondition 商铺条件
	 * @param pageIndex 第几页
	 * @param pageSize 每页几条
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
}
