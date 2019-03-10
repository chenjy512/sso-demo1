package com.cjy.sso.service;


import com.cjy.sso.pojo.User;

public interface UserService {
	
	public User findUserByUserName(String userName);
	
	public User findUserById(int id);
	
	public void cleanCache(String userName);

	public String testGet(String string);
}
