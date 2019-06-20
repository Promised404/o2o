package com.it888.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it888.o2o.entity.ShopCategory;

public interface ShopCategoryDao {
	
	/**
	 * 查找商铺分类列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
