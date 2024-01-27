package com.example.springjwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"com.example.springjwt"})
public class WebAppConfig {
	@Bean
	public InternalResourceViewResolver viewResolver() {
		
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
	
		vr.setPrefix("/WEB-INF/jsp/");
		vr.setSuffix(".jsp");
		
		return vr;
	}
}
