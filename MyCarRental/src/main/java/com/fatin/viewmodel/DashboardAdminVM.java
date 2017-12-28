package com.fatin.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import com.fatin.util.AdrStringUtil;

public class DashboardAdminVM {

	@Init
	public void init(){
		
	}
	
	@Command
	public void navigateToRequestApprovalPage(){
		AdrStringUtil.navigate("adminApprovalPage.zul");
	}
	
}
