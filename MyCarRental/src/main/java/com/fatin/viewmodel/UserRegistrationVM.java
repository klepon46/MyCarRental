package com.fatin.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.UserProfile;
import com.fatin.service.IUserProfileSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class UserRegistrationVM {

	@WireVariable
	private IUserProfileSvc iUserProfileSvc;
	
	private UserProfile userProfile;
	
	
	@Init
	public void init(){
		userProfile = new UserProfile();
	}
	
	@Command(value="save")
	public void save(){
		
		int id = Integer.parseInt(AdrStringUtil.generateIdSecond());
		userProfile.setId(id);
		
		iUserProfileSvc.save(userProfile);
	}


	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
}
