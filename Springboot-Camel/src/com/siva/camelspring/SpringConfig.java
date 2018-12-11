package com.siva.camelspring;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

  @Bean
  public CamelContext c() throws Exception {
    CamelContext c = new DefaultCamelContext();

    // NOTE: U can add the route here or in separate class and use addRoutes method

    /*
     * c.addRoutes(new RouteBuilder() {
     * 
     * @Override public void configure() throws Exception {
     * fromF("timer://simpleTimer?delay=2s&repeatCount=2")
     * 
     * .log("done,success");
     * 
     * }});
     */



    c.addRoutes(new CamelRouteComponent());



    return c;
  }



}
