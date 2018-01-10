package com.test.project2middleware.configuration;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitialization extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.out.println("I am in getRootConfigClasses");
		return new Class[] { WebConfig.class };

	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("I am in getServletConfigClasses");
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("I am in getServletMappings");
		return new String[] { "/" };
	}

}