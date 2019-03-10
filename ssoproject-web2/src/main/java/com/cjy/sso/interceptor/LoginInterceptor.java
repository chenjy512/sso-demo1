package com.cjy.sso.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjy.sso.util.CookiesGetUtil;
import com.cjy.sso.util.HttpUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 微笑
 * @description 登录拦截处理
 * @date 2018-04-09
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, Object arg2, Exception arg3)
            throws Exception {
         
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {
    	System.out.println("进入了web1拦截器================");
        String urlString = request.getRequestURI();
        //请求的路径
        String contextPath=request.getContextPath();
        String method = request.getMethod();
        System.out.println("==========method:"+method);
        if(urlString.endsWith("login")&&"POST".equals(method)){
            return true;
        }else{
        	//如果不是登录方法 那么就要去sso服务器验证是否登录过 没有登录过返回到登录页面 登录过就通过

            //1.获取cookie中存放的token，如果token为空则表示未登陆，或者token过期
        	String tokenVal = CookiesGetUtil.getCookies("wzw_token", request);
            System.out.println("拦截获取token信息----："+ tokenVal);
			if(StringUtils.isEmpty(tokenVal)){
//				response.sendRedirect(contextPath + "/index");
				request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request,response);
				return false;
			}


			//2. token存在检验是否有效
			Map<String,String> params = new HashMap<>();
	    	params.put("token", tokenVal);

        	String result1 = HttpUtil.send("http://127.0.0.1:8081/sso/user/auth",params,"utf-8");
        	JSONObject obj = JSON.parseObject(result1);

        	//3. 有效则放行
        	if(obj.getInteger("code")==200){
        		//登录成功 放行
        		//request.getRequestDispatcher("/WEB-INF/jsp/web1Main.jsp").forward(request,response);
        		return true;
        	}else{
        		//4. token无效，请重新登陆
//        		response.sendRedirect(contextPath + "/index");
        		request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request,response);
        		return false;
        	}
        }

    }

}