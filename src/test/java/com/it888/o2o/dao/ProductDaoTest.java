package com.it888.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.it888.o2o.BaseTest;
import com.it888.o2o.entity.Product;
import com.it888.o2o.entity.ProductCategory;
import com.it888.o2o.entity.Shop;

public class ProductDaoTest extends BaseTest{

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testInsertProduct() {
		Product product = new Product();
		product.setCreateTime(new Date());
		product.setEnableStatus(0);
		product.setImgAddr("测试");
		product.setLastEditTime(new Date());
		product.setNormalPrice("测试");
		product.setPriority(10);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(3L);
		product.setProductCategory(productCategory);
		product.setProductDesc("测试描述");
		product.setProductName("测试商品名称");
		product.setPromotionPrice("全是测试");
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		int count = productDao.insertProduct(product);
		System.out.println("插入" + count + "条记录");
	}
	
	@Test
	public void testQueryProductById(){
		Product queryProductById = productDao.queryProductById(1L);
		System.out.println(queryProductById.getProductName());
	}
	
	@Test
	public void testUpdateProduct(){
		Product product = productDao.queryProductById(1L);
		product.setProductName("修改后的名字");
		int i = productDao.updateProduct(product);
		System.out.println("修改了" + i);
	}
	
	@Test
	public void testQueryProductList(){
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		List<Product> list = productDao.queryProductList(product, 0, 10);
		for (Product product2 : list) {
			System.out.println(product2.getProductName());
		}
	}
	
	@Test
	public void testQueryProductCount(){
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		int count = productDao.queryProductCount(product);
		System.out.println(count);
	}
	
	@Test
	public void testUpdateProductCategoryToNull(){
		int count = productDao.updateProductCategoryToNull(10L);
		System.out.println(count);
	}
}
