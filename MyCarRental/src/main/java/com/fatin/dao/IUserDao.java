package com.fatin.dao;

import java.util.List;

import com.fatin.model.User;

public interface IUserDao {

	public void save(User domain);
	public User findUserByUserName(String userName);
	public User findByUserID(int userID);
	public List<User> findAllUserByRole(String role);
}
