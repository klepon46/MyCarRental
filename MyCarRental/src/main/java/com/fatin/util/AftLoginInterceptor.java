package com.fatin.util;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

/**
 * Class yang dieksekusi sebelum halaman page di load mengecek apakah user sudah
 * login, jika sudah maka user tidak mengakses halaman login lagi
 * 
 */
public class AftLoginInterceptor implements Initiator {

	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		LoginManager lm = LoginManager.getIntance();

		if (lm.isAuthenticated()) {
			Executions.sendRedirect("index.zul");
		}

	}

}
