package com.fatin.viewmodel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menu;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;
import com.fatin.util.LoginManager;

@VariableResolver(DelegatingVariableResolver.class)
public class IndexVM extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private IUserSvc iUserSvc;
	
	@Wire
	private Menu menu;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		int userID = (Integer) Executions.getCurrent().getSession().getAttribute("id");

		User user = iUserSvc.findByUserID(userID);
		menu.setLabel("Wecome, " + user.getUserName());
		
		String role = user.getRole();

		if (role.equalsIgnoreCase("USER")) {
			AdrStringUtil.navigate("dashboardUser.zul");
		} else if (role.equalsIgnoreCase("ADMIN")) {
			AdrStringUtil.navigate("dashboardAdmin.zul");
		} else if (role.equalsIgnoreCase("COMPANY")) {
			AdrStringUtil.navigate("dashboardCompany.zul");
		}

	}
	
	@Listen("onClick=#logout")
	public void logoutClicked() {
		LoginManager lm = LoginManager.getIntance();
		lm.logOff();
		Executions.sendRedirect("login.zul");
	}

}
