package com.it888.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码效验
 * @author 邓鹏涛
 *
 */
public class CodeUtil {
	/**
	 * 验证码效验
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request){
		String verifyCodeExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)){
			return false;
		}
		return true;
	}
}
