package com.cjy.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjy.sso.util.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("web2")
public class LoginController {
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response)throws Exception{
		//����ǵ�¼����ȥ����sso���������õ�¼�ӿ�
    	Map<String,String> params = new HashMap<>();
    	String userName = request.getParameter("userName");
	    	String passWd = request.getParameter("passWd");
    	params.put("userName", userName);
    	params.put("passWd", passWd);
    	String result = HttpUtil.send("http://127.0.0.1:8081/sso/user/login", params,"utf-8");
    	System.out.println("web1 ��¼���ص�����:"+result);
    	//JSONObject obj = JSON.parseObject(result);
    	JSONObject obj = JSON.parseObject(result);
		System.out.println("obj:------------"+obj);
    	if(obj.getInteger("code")==200){
    		//��¼�ɹ�
    		/** ��tokenд��ͻ���������  **/
    		Cookie clientCookie = new Cookie("wzw_token", obj.getString("obj"));
    		clientCookie.setMaxAge(60*30);
    		clientCookie.setPath("/");
    		response.addCookie(clientCookie);

    		return obj;
    	}else{
    		//��¼ʧ��
    		return obj;
    	}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginget(HttpServletRequest request)throws Exception{
		
		//������ǵ�¼���� ��ô��Ҫȥsso��������֤�Ƿ��¼�� û�е�¼�����ص���¼ҳ�� ��¼����ͨ��
//    	String tokenVal = CookiesGetUtil.getCookies("wzw_token", request);
//		if(StringUtils.isEmpty(tokenVal)){
//			return "index";
//		}
//    	
//		Map<String,String> params = new HashMap<>();
//    	params.put("token", tokenVal);
//    	
//    	String result1 = HttpUtil.send("http://127.0.0.1:8080/sso/user/auth",params,"utf-8");
//    	JSONObject obj = JSON.parseObject(result1);
//    	if(obj.getInteger("code")==200){
//    		return "web1Main";
//    	}else{
    		return "index";
//    	}
		
	}
}
