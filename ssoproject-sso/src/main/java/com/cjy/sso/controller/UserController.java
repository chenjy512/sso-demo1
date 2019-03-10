package com.cjy.sso.controller;


import com.cjy.sso.pojo.User;
import com.cjy.sso.service.UserService;
import com.cjy.sso.util.ResultVoUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author 微笑
 * @decription 用户认证的controller
 * @date 2018-04-09
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 用户授权接口
	 * @param request
	 * @return ResultVoUtil
	 */
	@RequestMapping(value="auth",method=RequestMethod.POST)
	@ResponseBody
	public ResultVoUtil auth(HttpServletRequest request, HttpServletResponse response){
		try {
			String tokenVal = request.getParameter("token");
			//查询是否有用户信息
			Object redisUser = redisTemplate.opsForValue().get(tokenVal);
			if(redisUser==null) {
				return ResultVoUtil.build(-2, "用户登录凭证错误", "用户登录凭证错误");
			}
			//更新缓存的时间
			updateCache(tokenVal,(User)redisUser);
			return ResultVoUtil.buildIsOk("该凭证授权成功!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultVoUtil.buildIsFail(e.getMessage());
		}
	}
	
	/**
	 * 用户统一登录接口
	 * @param request
	 * @return ResultVoUtil
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public ResultVoUtil login(HttpServletRequest request,HttpServletResponse response){
		try {
			String sign = "wzw_123";
			String userName = request.getParameter("userName");
			String passWd = request.getParameter("passWd");
			
			if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(passWd)){
				return ResultVoUtil.build(-1, "用户名和密码不能为空", null);
			}
			
			User dbUser = userService.findUserByUserName(userName.trim());
			
			if(dbUser==null){
				return ResultVoUtil.build(-2, "用户名或密码错误", null);
			}
			
			if(!dbUser.getPassWd().trim().equals(passWd.trim())){
				return ResultVoUtil.build(-2, "用户名或密码错误", null);
			}else{
				//登录成功
				//需要生成登录成功的凭证token 返回给客户端 同时redis服务器保存用户信息 token作为redis的key 用户基本信息作为对应的value
				//1.token生产规则  MD5(userName+passWd+Sign)
				String key = DigestUtils.md5Hex(dbUser.getUserName()+dbUser.getPassWd()+sign);
				updateCache(key,dbUser);
				return ResultVoUtil.buildIsOk(key);//将token返回到客户端程序
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultVoUtil.buildIsFail(e.getMessage());
		}
	}
	
	/**
	 * 设置或者更新缓存  缓存默认保存半个小时
	 * @param key
	 * @param dbUser
	 */
	public void updateCache(String key,User dbUser){
		redisTemplate.opsForValue().set(key, dbUser,60*30,TimeUnit.SECONDS);//设置到缓存 默认半个小时
	}
}
