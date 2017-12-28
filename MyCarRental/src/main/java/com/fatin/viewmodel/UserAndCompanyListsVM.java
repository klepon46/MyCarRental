package com.fatin.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;

@VariableResolver(DelegatingVariableResolver.class)
public class UserAndCompanyListsVM {

	@WireVariable
	private IUserSvc iUserSvc;
	
	private List<User> users;
	private List<User> companies;
	
	@Init
	public void init(){
		users = iUserSvc.findAllUserByRole("USER");
		companies = iUserSvc.findAllUserByRole("COMPANY");
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getCompanies() {
		return companies;
	}

	public void setCompanies(List<User> companies) {
		this.companies = companies;
	}
	
}
