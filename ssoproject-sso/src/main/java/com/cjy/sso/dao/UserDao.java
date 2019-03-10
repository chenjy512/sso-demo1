package com.cjy.sso.dao;


import com.cjy.sso.pojo.User;

public interface UserDao {

	public User findUserByUserName(String userName);
	
	public User findUserById(int id);
	
}
