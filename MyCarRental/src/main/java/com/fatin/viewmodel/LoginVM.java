package com.fatin.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;
import com.fatin.util.LoginManager;

@VariableResolver(DelegatingVariableResolver.class)
public class LoginVM {

	
	
	@WireVariable
	private IUserSvc iUserSvc;
	
	private User user;
	
	@Init
	public void init(){
		user = new User();
	}
	
	@Command
	public void doLogin(){
		LoginManager lm = LoginManager.getIntance();
		lm.logIn(user.getUserName(), user.getPassword());
		
		if(lm.isAuthenticated()){
			Executions.getCurrent().getSession()
			.setAttribute("id",lm.getUser().getId());
			Executions.sendRedirect("index.zul");
		}else{
			Clients.showNotification("Wrong username and password");
		}
		
	}
	
	@Command
	public void showPopupUserRegistration(){
		Executions.createComponents("/WEB-INF/lov/userRegLov.zul", null, null);
	}
	
	@Command
	public void sendCompanyUsernameRequest(){
		int id = Integer.parseInt(AdrStringUtil.generateIdSecond());
		user = new User();
		user.setId(id);	
		user.setIsApprove(0);
		user.setRole("COMPANY");
		user.setPassword("123");
		
		iUserSvc.save(user);
		Clients.showNotification("Send request to system", "info", 
				null, null, 1500);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
