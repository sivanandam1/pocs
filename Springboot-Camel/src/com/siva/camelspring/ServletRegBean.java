package com.siva.camelspring;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ServletRegBean {

	@Bean
	public ServletRegistrationBean sb()
	{
		ServletRegistrationBean s=new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
		s.setName("CamelServlet");
		return s;
	}
	
}
