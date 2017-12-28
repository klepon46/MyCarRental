package com.fatin.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;

@VariableResolver(DelegatingVariableResolver.class)
public class AdminApprovalPageVM {

	@WireVariable
	private IUserSvc iUserSvc;

	private List<User> users;
	private User user;

	@Init
	public void init() {
		users = iUserSvc.findAllUserByRole("COMPANY");
	}

	@Command
	@NotifyChange("users")
	public void approveRequest() {
		user.setIsApprove(1);
		iUserSvc.save(user);

		Clients.showNotification("Company user credential created", "info", null, null, 1500);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
