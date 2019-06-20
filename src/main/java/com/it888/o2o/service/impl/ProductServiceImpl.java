package com.it888.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.it888.o2o.dao.ProductDao;
import com.it888.o2o.dao.ProductImgDao;
import com.it888.o2o.dto.ProductExecution;
import com.it888.o2o.entity.Product;
import com.it888.o2o.entity.ProductImg;
import com.it888.o2o.enums.ProductStateEnum;
import com.it888.o2o.exception.ProductOperationException;
import com.it888.o2o.service.ProductService;
import com.it888.o2o.util.ImageUtil;
import com.it888.o2o.util.PageCalculator;
import com.it888.o2o.util.PathUtil;

/**
 * 商品Service
 * @author 邓鹏涛
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, CommonsMultipartFile productImg,List<CommonsMultipartFile> productImgFileList) {
		//空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//初始化信息
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认为上架
			product.setEnableStatus(1);
			if (productImg != null) {
				addProductImg(product, productImg);
			}
			try{
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException(ProductStateEnum.INSERT_FAIL.getStateInfo());
				}
			}catch (Exception e) {
				throw new ProductOperationException(ProductStateEnum.INSERT_FAIL.getStateInfo() + e.getMessage());
			}
			//商品详情图不为空则添加
			if (productImgFileList != null) {
				addProductImgList(product,productImgFileList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else{
			return new ProductExecution(ProductStateEnum.NULL_PRODUCT);
		}
	}

	/**
	 * 添加缩略图
	 * @param shop
	 * @param shopImg
	 */
	private void addProductImg(Product product, CommonsMultipartFile productImg) {
		//获取product图片目录的相对值路径
		String dest = PathUtil.getProductImagePath(product.getShop().getShopId());
		String ProductImgAddr = ImageUtil.generateThumbnail(productImg, dest);
		product.setImgAddr(ProductImgAddr);
	}
	
	/**
	 * 批量添加图片
	 * @param product
	 * @param productImgList
	 * @param productImgFileList
	 */
	private void addProductImgList(Product product,List<CommonsMultipartFile> productImgFileList){
		//获取product图片目录的相对值路径
		String dest = PathUtil.getProductListImagePath(product.getProductId());
		List<String> imgAddrList = ImageUtil.generateNormalImgs(productImgFileList, dest);
		if (imgAddrList != null && imgAddrList.size() > 0) {
			List<ProductImg> productImgList= new ArrayList<ProductImg>();
			//遍历图片一次去处理，并添加进productImg实体类里
			for (String imgAddr : imgAddrList) {
				ProductImg productImg = new ProductImg();
				productImg.setCreateTime(new Date());
				productImg.setImgAddr(imgAddr);
				productImg.setProductId(product.getProductId());
				productImgList.add(productImg);
			}
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException(ProductStateEnum.BATCH_INSET_FAIL.getStateInfo());
				}
			} catch (ProductOperationException e) {
				throw new ProductOperationException(ProductStateEnum.BATCH_INSET_FAIL.getStateInfo() + e.toString());
			}
		} else {
			throw new ProductOperationException("生成图片地址出错...");
		}
		
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}
	
	/**
	 * 删除某个商品下所有的详情图
	 */
	public void deleteProductImgList(long productId) {
		//根据productId
		List<ProductImg> imgList = productImgDao.queryProductImgList(productId);
		//删除原来的图片
		for (ProductImg productImg : imgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		//删除数据库里有的图片信息
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, CommonsMultipartFile productImg,
			List<CommonsMultipartFile> productImgFileList) throws ProductOperationException{
		
		//空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//给商品默认属性
			product.setLastEditTime(new Date());
			//诺商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if (productImg != null) {
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				//删除后新增图片
				addProductImg(product,productImg);
			}
			//判断是否有新传入的商品详情图片
			if(productImgFileList != null && productImgFileList.size() > 0){
				//有就先将以前的商品详情图片删除
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgFileList);
			}
			//修改完后更新商品信息
			try{
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息出错");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			}catch (Exception e) {
				throw new ProductOperationException("更新商品信息失败：" + e.toString());
			}	
		} else {
			throw new ProductOperationException("要修改的商品为空,检查传进来的商品状态");
		}		
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		//页码转换成数据库第几行数据，并调用dao层取回指定页码的商品列表
		ProductExecution pe = new ProductExecution();
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
//		Shop shop = new Shop();
//		shop.setShopId(1L);
//		Product productTest = new Product();
//		productTest.setShop(shop);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		//List<Product> productList = productDao.queryProductList(productTest, rowIndex, pageSize);
		//基于同样查询条件返回
		int count = productDao.queryProductCount(productCondition);
		if (productList != null) {
			pe.setProductList(productList);
			pe.setCount(count);
		}else{
			pe.setStateInfo(ProductStateEnum.PRODUCT_NULL.getStateInfo());
		}
		return pe;
	}

}
