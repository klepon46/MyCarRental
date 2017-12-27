package com.fatin.dao;

import com.fatin.model.User;

public interface IUserDao {

	public void save(User domain);
	public User findUserByUserName(String userName);
	
}
