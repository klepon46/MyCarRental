package com.fatin.lov;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class UserRegLov {
	
	@WireVariable
	private IUserSvc iUserSvc;

	private User user;
	
	@Init
	public void init(){
		user = new User();
	}
	
	@Command
	public void saveUser(@BindingParam("win") Window window){
		int id = Integer.parseInt(AdrStringUtil.generateIdSecond());
		user.setRole("USER");
		user.setId(id);
		user.setIsApprove(1);
		
		iUserSvc.save(user);
		window.detach();
		
		Clients.showNotification("Success", "succes", null, null, 1500);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
