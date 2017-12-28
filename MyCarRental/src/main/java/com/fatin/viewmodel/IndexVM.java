package com.fatin.viewmodel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Include;

import com.fatin.model.User;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class IndexVM extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable
	private IUserSvc iUserSvc;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		int userID = (Integer) Executions.getCurrent().getSession().getAttribute("id");

		User user = iUserSvc.findByUserID(userID);
		String role = user.getRole();

		if (role.equalsIgnoreCase("USER")) {
			navigate("dashboardUser.zul");
		} else if (role.equalsIgnoreCase("ADMIN")) {
			AdrStringUtil.navigate("dashboardAdmin.zul");
		} else if (role.equalsIgnoreCase("COMPANY")) {

		}

	}

	public void navigate(String path) {
		Include include = (Include) Executions.getCurrent().getDesktop().getPage("index").getFellow("mainmenu");
		include.setSrc(path);
	}

}
