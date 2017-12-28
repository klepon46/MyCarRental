package com.fatin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatin.dao.IUserDao;
import com.fatin.model.User;

@Service(value="iUserSvc")
@Transactional
public class IUserSvcImpl implements IUserSvc {

	@Autowired
	private IUserDao dao;
	
	public void save(User domain) {
		dao.save(domain);
	}

	public User findUserByUserName(String userName) {
		return dao.findUserByUserName(userName);
	}

	public User findByUserID(int userID) {
		return dao.findByUserID(userID);
	}

	public List<User> findAllUserByRole(String role) {
		return dao.findAllUserByRole(role);
	}

}
