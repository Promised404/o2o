package com.it888.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 商铺管理员Controller(视图跳转)
 * @author 邓鹏涛
 *
 */
@Controller
@RequestMapping(value = "/shopadmin", method=RequestMethod.GET)
public class ShopAdminController {
	
	/**
	 * 视图转发
	 * @return
	 */
	@RequestMapping(value="/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
	
	@RequestMapping(value="/shoplist")
	public String shopList() { 
		return "shop/shoplist";
	}
	
	@RequestMapping(value="/shopmanagement")
	public String shopManagement() { 
		return "shop/shopmanagement";
	}

	@RequestMapping(value="/productcategorymanagement")
	public String productCategorymanage() { 
		return "shop/productcategorymanagement";
	}
	
	@RequestMapping(value="/productoperation")
	public String productOperation() { 
		return "shop/productoperation";
	}
	
	@RequestMapping(value="/productmanagement")
	public String productManagement(){
		return "shop/productmanagement";
	}
}
