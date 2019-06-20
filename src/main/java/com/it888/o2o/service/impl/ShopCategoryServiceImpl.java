package com.it888.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it888.o2o.dao.ShopCategoryDao;
import com.it888.o2o.entity.ShopCategory;
import com.it888.o2o.service.ShopCategoryService;

/**
 * 商铺类别Service
 * @author 邓鹏涛
 *
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> queryShopCategory(ShopCategory shopCategory) {
		return shopCategoryDao.queryShopCategory(shopCategory);
	}

}
