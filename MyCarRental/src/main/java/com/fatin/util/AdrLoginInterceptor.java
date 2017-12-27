package com.fatin.util;

import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

/**
 * Class yang dieksekusi sebelum halaman page di load
 * mengecek apakah user sudah login, jika sudah maka 
 * user tidak mengakses halaman login lagi
 * 
 */
public class AdrLoginInterceptor implements Initiator{

	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Executions.sendRedirect("/index.zul");
		}
	}

}
