package com.it888.o2o.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.it888.o2o.BaseTest;
import com.it888.o2o.entity.Area;
import com.it888.o2o.entity.PersonInfo;
import com.it888.o2o.entity.Shop;
import com.it888.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test地址");
		shop.setPhone("test电话");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中...");
		shop.setPriority(1);
		int effectedNum = shopDao.insertShop(shop);
		Assert.assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop(){
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		Assert.assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testQueryByShop(){
		Shop shop = shopDao.queryByShopId(1);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getArea().getAreaName());
	}
	
	@Test
	public void testQueryShopList(){
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> list = shopDao.queryShopList(shopCondition,0,5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("商铺几条信息：" + list.size());
		System.out.println("一共有几个商铺:" + count);
	}
	
}
