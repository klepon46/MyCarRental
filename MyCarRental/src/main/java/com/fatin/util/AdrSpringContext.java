package com.fatin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AdrSpringContext implements ApplicationContextAware {

	
	private static ApplicationContext Context;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		Context = arg0;
	}
	
	public static Object getBean(String beanName){
		return Context.getBean(beanName);
	}

}
