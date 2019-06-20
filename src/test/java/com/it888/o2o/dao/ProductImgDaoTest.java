package com.it888.o2o.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.it888.o2o.BaseTest;
import com.it888.o2o.entity.ProductImg;

public class ProductImgDaoTest extends BaseTest{
	
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	public void batchInsertProductImgTest(){
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		ProductImg productImg = new ProductImg();
		productImg.setCreateTime(new Date());
		productImg.setImgAddr("测试地址");
		productImg.setImgDesc("测试描述");
		productImg.setPriority(1);
		productImg.setProductId(1L);
		ProductImg productImg2 = new ProductImg();
		productImg2.setCreateTime(new Date());
		productImg2.setImgAddr("测试地址2");
		productImg2.setImgDesc("测试描述2");
		productImg2.setPriority(2);
		productImg2.setProductId(1L);
		productImgList.add(productImg);
		productImgList.add(productImg2);
		int count = productImgDao.batchInsertProductImg(productImgList);
		System.out.println("插入的数量：" + count);
	}
	
	@Test
	public void testDeleteProductImgByProductId(){
		int count = productImgDao.deleteProductImgByProductId(1L);
		System.out.println("影响的行数：" + count);
	}
	
	@Test
	public void testQueryProductImgList() {
		List<ProductImg> list = productImgDao.queryProductImgList(2L);
		System.out.println(list.size());
	}
}
