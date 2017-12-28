package com.fatin.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class ProfilePageVM {

	@WireVariable
	private IUserSvc iUserSvc;

	private User user;

	@Init
	public void init() {
		user = iUserSvc.findByUserID(AdrStringUtil.getCurrentUserID());
	}

	@Command
	public void onUpdateProfile() {
		iUserSvc.save(user);
		Clients.showNotification("Success", "info", null, null, 1500);
	}

	@Command
	public void backToDashboard() {
		AdrStringUtil.navigate("dashboardUser.zul");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
