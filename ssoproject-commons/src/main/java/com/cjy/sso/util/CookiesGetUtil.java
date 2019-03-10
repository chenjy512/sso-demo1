package com.cjy.sso.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookiesGetUtil {
	
	/**
	 * ªÒ»°cookie
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getCookies(String key,HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(null==cookies||cookies.length<0){
			return null;
		}
		
		String tokenVal = "";
		for(Cookie cookie:cookies){
			String name = cookie.getName();
			if(!StringUtils.isEmpty(name)&&key.equals(name)){
				tokenVal = cookie.getValue();
			}
		}
		
		return tokenVal;
	}
	
}
