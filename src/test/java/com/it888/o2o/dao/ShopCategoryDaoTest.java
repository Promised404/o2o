package com.it888.o2o.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.it888.o2o.BaseTest;
import com.it888.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testQueryShopCategory(){
		List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
		Assert.assertEquals(2, list.size());
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory parentShopCategory = new ShopCategory();
		parentShopCategory.setShopCategoryId(2L);
		shopCategory.setParent(parentShopCategory);
		list = shopCategoryDao.queryShopCategory(shopCategory);
		Assert.assertEquals(1, list.size());
	}
}
