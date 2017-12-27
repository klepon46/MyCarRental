package com.fatin.viewmodel;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Include;

public class IndexVM extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		int userID = (Integer) Executions.getCurrent().getSession().getAttribute("id");

		navigate("dashboardUser.zul");

	}

	public void navigate(String path) {
		Include include = (Include) Executions.getCurrent().getDesktop().getPage("index").getFellow("mainmenu");
		include.setSrc(path);
	}

}
