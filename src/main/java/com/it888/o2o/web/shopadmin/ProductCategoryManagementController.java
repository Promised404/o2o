package com.it888.o2o.web.shopadmin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it888.o2o.dto.ExecutionO2o;
import com.it888.o2o.dto.ProductCategoryExecution;
import com.it888.o2o.dto.Result;
import com.it888.o2o.entity.ProductCategory;
import com.it888.o2o.entity.Shop;
import com.it888.o2o.enums.ProductCategoryStateEnum;
import com.it888.o2o.exception.ProductCategoryOperationException;
import com.it888.o2o.service.ProductCategoryService;

/**
 * 商品分类Controller
 * 
 * @author 邓鹏涛
 *
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		// 获取session中店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		//currentShop = new Shop();
		currentShop.setShopId(currentShop.getShopId());
		// 判断是否有店铺信息
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true, list);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.NULL_PRODUCT;
			return new Result<List<ProductCategory>>(false, ps.getStateInfo(), ps.getState());
		}

	}

	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 遍历商品类别，并且为其设置当前用户id和创建时间
		for (ProductCategory productCategory : productCategoryList) {
			productCategory.setCreateTime(new Date());
			productCategory.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution productCategoryExecution = productCategoryService.batchInsertProductCategory(productCategoryList);
				if (productCategoryExecution.getState() == ProductCategoryStateEnum.INSERT_SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", productCategoryExecution.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		ExecutionO2o<ProductCategory> pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
		if (productCategoryId != null && productCategoryId > 0) {
			try{
				if (pe.getState() == ProductCategoryStateEnum.DELETE_SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一样商品类别");
		}
		return modelMap;
	}
}
