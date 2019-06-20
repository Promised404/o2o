package com.it888.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.it888.o2o.dao.ShopDao;
import com.it888.o2o.dto.ShopExecution;
import com.it888.o2o.entity.Shop;
import com.it888.o2o.enums.ShopStateEnum;
import com.it888.o2o.exception.ShopOperationException;
import com.it888.o2o.service.ShopService;
import com.it888.o2o.util.ImageUtil;
import com.it888.o2o.util.PageCalculator;
import com.it888.o2o.util.PathUtil;

/**
 * 店铺Service
 * @author 邓鹏涛
 *
 */
@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		//判断商品状态
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		}
		//插入商品
		try{
			shop.setEnableStatus(0);//建议使用枚举类型
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			//判断是否插入成功
			if(effectedNum <= 0) {
				//此处使用RuntimeException,事务会回滚
				throw new ShopOperationException("店铺创建异常...");
			}else{
				if(shopImg != null) {
					//存储图片
					try{
						addShopImg(shop,shopImg);
					}catch (Exception e) {
						throw new ShopOperationException("店铺图片创建失败..." + e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败...");
					}
				}
			}
		}catch (Exception e) {
			throw new ShopOperationException("店铺创建异常..." + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHCKE,shop);
	}

	/**
	 * 新增图片
	 * @param shop
	 * @param shopImg
	 */
	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		//获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg) {
		//先判断是否为空
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOPID);
		}else{
			//判断是否需要图片处理，如果有，首先删除原有的图片，在新增
			try{
				if(shopImg != null){
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if(tempShop.getShopImg() != null){
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop,shopImg);
				}
				//更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if(effectedNum <= 0){
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				}else{
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS,shop);
				}
			}catch (Exception e) {
				throw new ShopOperationException("modifyShop error" + e.getMessage());
			}
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null){
			se.setShopList(shopList);
			se.setCount(count);
		}else{
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
