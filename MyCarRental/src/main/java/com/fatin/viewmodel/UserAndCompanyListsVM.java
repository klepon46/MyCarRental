package com.fatin.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;

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
	
	@Command
	public void showCompanyCars(@BindingParam("me") User user){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", user.getId());
		
		Executions.createComponents("/WEB-INF/lov/carCompanyLov.zul", null, map);
	}
	
	@Command
	public void back(){
		AdrStringUtil.navigate("dashboardAdmin.zul");
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
