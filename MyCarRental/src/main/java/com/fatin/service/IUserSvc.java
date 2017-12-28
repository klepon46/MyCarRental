package com.fatin.service;

import java.util.List;

import com.fatin.model.User;

public interface IUserSvc {

	public void save(User domain);
	public User findUserByUserName(String userName);
	public User findByUserID(int userID);
	public List<User> findAllUserByRole(String role);
}
