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
 * @author ΢Ц
 * @decription �û���֤��controller
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
	 * �û���Ȩ�ӿ�
	 * @param request
	 * @return ResultVoUtil
	 */
	@RequestMapping(value="auth",method=RequestMethod.POST)
	@ResponseBody
	public ResultVoUtil auth(HttpServletRequest request, HttpServletResponse response){
		try {
			String tokenVal = request.getParameter("token");
			//��ѯ�Ƿ����û���Ϣ
			Object redisUser = redisTemplate.opsForValue().get(tokenVal);
			if(redisUser==null) {
				return ResultVoUtil.build(-2, "�û���¼ƾ֤����", "�û���¼ƾ֤����");
			}
			//���»����ʱ��
			updateCache(tokenVal,(User)redisUser);
			return ResultVoUtil.buildIsOk("��ƾ֤��Ȩ�ɹ�!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultVoUtil.buildIsFail(e.getMessage());
		}
	}
	
	/**
	 * �û�ͳһ��¼�ӿ�
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
				return ResultVoUtil.build(-1, "�û��������벻��Ϊ��", null);
			}
			
			User dbUser = userService.findUserByUserName(userName.trim());
			
			if(dbUser==null){
				return ResultVoUtil.build(-2, "�û������������", null);
			}
			
			if(!dbUser.getPassWd().trim().equals(passWd.trim())){
				return ResultVoUtil.build(-2, "�û������������", null);
			}else{
				//��¼�ɹ�
				//��Ҫ���ɵ�¼�ɹ���ƾ֤token ���ظ��ͻ��� ͬʱredis�����������û���Ϣ token��Ϊredis��key �û�������Ϣ��Ϊ��Ӧ��value
				//1.token��������  MD5(userName+passWd+Sign)
				String key = DigestUtils.md5Hex(dbUser.getUserName()+dbUser.getPassWd()+sign);
				updateCache(key,dbUser);
				return ResultVoUtil.buildIsOk(key);//��token���ص��ͻ��˳���
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResultVoUtil.buildIsFail(e.getMessage());
		}
	}
	
	/**
	 * ���û��߸��»���  ����Ĭ�ϱ�����Сʱ
	 * @param key
	 * @param dbUser
	 */
	public void updateCache(String key,User dbUser){
		redisTemplate.opsForValue().set(key, dbUser,60*30,TimeUnit.SECONDS);//���õ����� Ĭ�ϰ��Сʱ
	}
}
