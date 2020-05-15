package com.it888.o2o.dao;

import java.util.List;

import com.it888.o2o.entity.ProductImg;

public interface ProductImgDao {

	/**
	 * 批量添加商品图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	/**
	 * 删除指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
	
	/**
	 * 查询商品详图列表
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);
}