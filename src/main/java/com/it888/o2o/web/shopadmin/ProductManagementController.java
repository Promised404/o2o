package com.it888.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it888.o2o.dto.ProductExecution;
import com.it888.o2o.entity.Product;
import com.it888.o2o.entity.ProductCategory;
import com.it888.o2o.entity.Shop;
import com.it888.o2o.enums.ProductStateEnum;
import com.it888.o2o.service.ProductCategoryService;
import com.it888.o2o.service.ProductService;
import com.it888.o2o.util.CodeUtil;
import com.it888.o2o.util.HttpServletRequestUtil;

/**
 * 商品管理Controller
 * @author 邓鹏涛
 *
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	//支持商品详情图的最大上传数量
	private static final int IMAGEMAXCOUNT = 6;
	
	/**
	 * 添加商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProduct(HttpServletRequest request) {
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//验证码效验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//接受前端参数的变量初始化，包括商品，缩略图，详情列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile multipartFile = null;
		List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try{
			//诺请求中存在文件流，则取出相关文件（包括缩略图和详情图）
			if(multipartResolver.isMultipart(request)){
				multipartRequest = (MultipartHttpServletRequest)request;
				//取出缩略图，并且构建CommonsMultipartFile对象
				multipartFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				//取出详情图列表并构建List<CommonsMultipartFile>列表对象，最多支持上传六张图片
				for(int i = 0; i < IMAGEMAXCOUNT; i++){
					CommonsMultipartFile producImg = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
					if (producImg != null) {
						//诺取出的文件流不为空，则将其加入详情列表
						productImgs.add(producImg);
					}
				}
			}else{
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
			}
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try{
			//尝试获取前端传递过来的表单String流并将其转换成Product实体类
			product = mapper.readValue(productStr, Product.class);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//判断product数据是否为空
		if(product != null && multipartFile != null && productImgs.size() > 0){
			try{
				//从session中获取当前店铺的ID并赋值给product,减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				ProductExecution pe = productService.addProduct(product, multipartFile, productImgs);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
			return modelMap;
		}
		
		return modelMap;
	}
	
	/**
	 * 通过id获取商品
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductById(@RequestParam Long productId){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if (productId > -1) {
			//查询商品信息
			Product product = productService.getProductById(productId);
			//查询该商品下的商品类别
			List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductStateEnum.PARAM_ERRO);
		}
		return modelMap;
	}
	
	/**
	 * 修改商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//前端有两种修改方式,一种上下架操作无需验证码
		boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
		//验证码效验
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile multipartFile = null;
		List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try{
			if (multipartResolver.isMultipart(request)) {
				//TODO 此处代码可以重构
				multipartRequest = (MultipartHttpServletRequest)request;
				//取出缩略图，并且构建CommonsMultipartFile对象
				multipartFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				//取出详情图列表并构建List<CommonsMultipartFile>列表对象，最多支持上传六张图片
				for(int i = 0; i < IMAGEMAXCOUNT; i++){
					CommonsMultipartFile producImg = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
					if (producImg != null) {
						//诺取出的文件流不为空，则将其加入详情列表
						productImgs.add(producImg);
					}
				}
			}
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try{
			product = mapper.readValue(productStr, Product.class);
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (product != null){
			try{
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				ProductExecution pe = productService.modifyProduct(product, multipartFile, productImgs);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()){
					modelMap.put("success", true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}
		return modelMap;
	}
	
	@RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductListByShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//获取前台传过来的页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		//空值判断
		if((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)){
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
			//根据分页条件进行查询
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("success", true);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
		} else {
			modelMap.put("success",false);
			modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	
	private Product compactProductCondition(long shopId,long productCategoryId,String productName){
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		//诺指定了商品类别则添加进去
		if(productCategoryId != -1L){
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		//有商品模糊查询也添加进去
		if(productName != null){
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
}
