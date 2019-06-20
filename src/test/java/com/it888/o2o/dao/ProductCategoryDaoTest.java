package com.it888.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.it888.o2o.BaseTest;
import com.it888.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest{
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void queryProductCategoryListTest(){
		List<ProductCategory> list = productCategoryDao.queryProductCategoryList(1);
		System.out.println("该店铺自定义类别为：" + list.size());
	}
	
	@Test
	public void batchInsertProductCategoryTest(){
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		ProductCategory pro = new ProductCategory();
		pro.setPriority(10);
		pro.setCreateTime(new Date());
		pro.setProductCategoryName("商品测试类别1");
		pro.setShopId(1L);
		ProductCategory pro2 = new ProductCategory();
		pro2.setPriority(11);
		pro2.setCreateTime(new Date());
		pro2.setProductCategoryName("商品测试类别2");
		pro2.setShopId(1L);
		productCategoryList.add(pro);
		productCategoryList.add(pro2);
		int count = productCategoryDao.batchInsertProductCategory(productCategoryList);
		System.out.println(count);
	}
	
	@Test
	public void deleteProductCategoryTest(){
		int count = productCategoryDao.deleteProductCategory(1L, 1L);
		System.out.println("删除的数量：" + count);
//		long shopId = 1;
//		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
//		for (ProductCategory productCategory : productCategoryList) {
//			if () {
//				
//			}
//		}
	}
}
