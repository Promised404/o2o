package com.it888.o2o.service;

import java.util.List;

import com.it888.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	
	/**
	 * 查询商铺分类
	 * @param shopCategory
	 * @return
	 */
	List<ShopCategory> queryShopCategory(ShopCategory shopCategory);

}
