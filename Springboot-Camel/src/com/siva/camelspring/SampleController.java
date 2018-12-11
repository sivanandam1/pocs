package com.siva.camelspring;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.siva.camelspring"})
@SpringBootApplication
public class SampleController {

  @Autowired
  CamelContext c;

  @RequestMapping("/123")
  @ResponseBody
  String home() throws Exception {
    System.out.println("HIIII" + c.getRouteDefinitions());
    c.start();
    // c.getRouteDefinition("HIII").autoStartup(true);
    return "Hello World!";
  }

  public static void main(String[] args) throws Exception {

    SpringApplication.run(SampleController.class, args);


  }
}


