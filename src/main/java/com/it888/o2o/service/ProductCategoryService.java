package com.it888.o2o.service;

import java.util.List;

import com.it888.o2o.dto.ExecutionO2o;
import com.it888.o2o.dto.ProductCategoryExecution;
import com.it888.o2o.entity.ProductCategory;
import com.it888.o2o.exception.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 查询指定店铺下所有商铺的类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);
	
	/**
	 * 批量插入商品类别
	 * @param productCategoryList
	 * @return
	 */
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 将此类别下的商品里的类别id置为空，在删除该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	ExecutionO2o<ProductCategory> deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
