package com.cjy.sso.service.impl;

import com.cjy.sso.dao.UserDao;
import com.cjy.sso.pojo.User;
import com.cjy.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 缓存user信息 key为 @Cacheable注解对应的 value+key
	 */
	@Cacheable(value="user",key="#userName")
	public User findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		System.out.println("==============in findUserByUserName");
		return userDao.findUserByUserName(userName);
	}
	
	/**
	 * 缓存user信息 key为 @Cacheable注解对应的 value+key
	 */
	@Cacheable(value="user",key="#id")
	@Override
	public User findUserById(int id) {
		// TODO Auto-generated method stub
		System.out.println("==============in findUserById");
		return userDao.findUserById(id);
	}
	
	/**
	 * 删除key
	 */
	@CacheEvict(value={"user"},key="#userName")
	@Override
	public void cleanCache(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String testGet(String string) {
		// TODO Auto-generated method stub
		String val = (String)redisTemplate.opsForValue().get("name");
		return val;
	}
	
}
