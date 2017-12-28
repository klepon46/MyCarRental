package com.fatin.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.fatin.util.AdrStringUtil;

public class UserDashboardVM {

	@Init
	public void init(){
		
	}
	
	@Command
	public void navigateToUpdateProfilePage(){
		AdrStringUtil.navigate("profilePage.zul");
	}
	
}
