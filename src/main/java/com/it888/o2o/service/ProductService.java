package com.it888.o2o.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.it888.o2o.dto.ProductExecution;
import com.it888.o2o.entity.Product;
import com.it888.o2o.exception.ProductOperationException;

public interface ProductService {

	/**
	 * 新增商品
	 * @param product
	 * @param productImg
	 * @param productImgList
	 * @return
	 */
	ProductExecution addProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgFileList);

	/**
	 * 通过商品Id查询唯一商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);
	
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param productImg
	 * @param productImgFileList
	 * @return
	 */
	ProductExecution modifyProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgFileList) throws ProductOperationException;

	/**
	 * 分页查询所有商品
	 * @param productCondition
	 * @param pageIndex 第几页
	 * @param pageSize 每页显示几条
	 * @return
	 */
	ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
	
}
