package com.fatin.service;

import com.fatin.model.User;

public interface IUserSvc {

	public void save(User domain);
	public User findUserByUserName(String userName);
	public User findByUserID(int userID);
}
