package com.fatin.util;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;

public class LoginManager {
	private static final String KEY_USER_MODEL = LoginManager.class.getName() + "_MODEL";

	User user;

	IUserSvc context = (IUserSvc) AdrSpringContext.getBean("iUserSvc");

	private LoginManager() {

	}

	public static LoginManager getIntance() {
		return getIntance(Sessions.getCurrent());
	}

	public static LoginManager getIntance(Session zkSession) {

		synchronized (zkSession) {

			LoginManager loginModel = (LoginManager) zkSession.getAttribute(KEY_USER_MODEL);
			if (loginModel == null) {
				zkSession.setAttribute(KEY_USER_MODEL, loginModel = new LoginManager());
			}
			return loginModel;
		}
	}

	public synchronized void logIn(String name, String password) {

		User tempUser = context.findUserByUserName(name);

		if (tempUser != null && tempUser.getPassword().equals(password)) {
			user = tempUser;
		} else {
			user = null;
		}
	}

	public void logOff() {
		user = null;
	}

	public synchronized User getUser() {
		return user;
	}

	public synchronized boolean isAuthenticated() {
		return user != null;
	}
}
