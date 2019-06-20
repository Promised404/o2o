package com.it888.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it888.o2o.dao.ProductCategoryDao;
import com.it888.o2o.dao.ProductDao;
import com.it888.o2o.dto.ExecutionO2o;
import com.it888.o2o.dto.ProductCategoryExecution;
import com.it888.o2o.entity.ProductCategory;
import com.it888.o2o.enums.ProductCategoryStateEnum;
import com.it888.o2o.exception.ProductCategoryOperationException;
import com.it888.o2o.service.ProductCategoryService;

/**
 * 商品类别服务
 * @author 邓鹏涛
 *
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) {
		if(productCategoryList != null && productCategoryList.size() > 0){
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new ProductCategoryOperationException(ProductCategoryStateEnum.CREATE_FAIL.getStateInfo());
				}else{
					return new ProductCategoryExecution(ProductCategoryStateEnum.INSERT_SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
			}
			
		}else{
			return new ProductCategoryExecution(ProductCategoryStateEnum.NULL_LIST);
		}
	}

	@Override
	@Transactional
	public ExecutionO2o<ProductCategory> deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException{
		
		if(productCategoryId > -1){
			try{
				int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
				if(effectedNum < 0){
					throw new ProductCategoryOperationException(ProductCategoryStateEnum.UPDATE_CATEGORYID_ERRO.getStateInfo());
					//return new ProductCategoryExecution(ProductCategoryStateEnum.UPDATE_CATEGORYID_ERRO);
				}
			}catch (ProductCategoryOperationException e) {
				throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
			}
		}else{
			return new ExecutionO2o<ProductCategory>(ProductCategoryStateEnum.UPDATE_CATEGORYID_ERRO);
		}
		
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectedNum <= 0) {
				throw new ProductCategoryOperationException(ProductCategoryStateEnum.DELETE_FAIL.getStateInfo());
			} else {
				return new ExecutionO2o<ProductCategory>(ProductCategoryStateEnum.DELETE_SUCCESS);
			}
		}catch (ProductCategoryOperationException e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
		}
	}

}
