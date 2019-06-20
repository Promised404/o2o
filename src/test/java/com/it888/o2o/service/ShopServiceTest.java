package com.it888.o2o.service;

import java.io.File;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.it888.o2o.BaseTest;
import com.it888.o2o.dto.ShopExecution;
import com.it888.o2o.entity.Area;
import com.it888.o2o.entity.PersonInfo;
import com.it888.o2o.entity.Shop;
import com.it888.o2o.entity.ShopCategory;
import com.it888.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	@Ignore
	public void testAddShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺1");
		shop.setShopDesc("test1");
		shop.setShopAddr("test地址1");
		shop.setPhone("test电话");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHCKE.getState());
		shop.setAdvice("审核中...");
		File file = new File("D:/Java/upload/null.jpg");
		ShopExecution se = shopService.addShop(shop, null);
		Assert.assertEquals(ShopStateEnum.CHCKE.getState(), se.getState());
	}
	
	@Test
	@Ignore
	public void testModifyShop(){
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		ShopExecution modifyShop = shopService.modifyShop(shop,null);
		System.out.println(modifyShop.getShop().getShopName());
	}
	
	@Test
	public void testGetShopList(){
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution list = shopService.getShopList(shopCondition, 1, 2); 
		System.out.println(list.getShopList().size());
		System.out.println(list.getCount());
	}
}
