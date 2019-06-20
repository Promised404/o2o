package com.it888.o2o.util;

/**
 * 图片路径工具
 * @author 邓鹏涛
 *
 */
public class PathUtil {
	private static String separator = System.getProperty("file.separator");
	
	/**
	 * 返回项目图片的根路径
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")) {
			basePath = "D:/Java/upload";
		}else{
			basePath = "/home/image";
		}
		basePath = basePath.replace("/", separator);
		return basePath;
	}
	
	/**
	 * 依据不同的业务需求，项目图片的子路径（店铺）
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", separator);
	}
	
	/**
	 * 依据不同的业务需求，项目图片的子路径（商品缩略图）
	 * @param shopId
	 * @return
	 */
	public static String getProductImagePath(long shopId) {
		String imagePath = "/upload/item/product/" + shopId + "/";
		return imagePath.replace("/", separator);
	}
	
	/**
	 * 依据不同的业务需求，项目图片的子路径（商品详情图）
	 * @param shopId
	 * @return
	 */
	public static String getProductListImagePath(long shopId) {
		String imagePath = "/upload/item/productlist/" + shopId + "/";
		return imagePath.replace("/", separator);
	}
}
