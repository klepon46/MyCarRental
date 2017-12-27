package com.fatin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatin.dao.IUserProfileDao;
import com.fatin.model.UserProfile;

@Service(value="iUserProfileSvc")
@Transactional
public class UserProfileSvcImpl implements IUserProfileSvc{

	@Autowired
	private IUserProfileDao dao;
	
	public void save(UserProfile domain) {
		dao.save(domain);
	}

}
