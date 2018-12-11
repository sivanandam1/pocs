package com.siva.camelspring;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class CamelRouteComponent extends RouteBuilder {

  @Override
  public void configure() throws Exception {


    ////////////////////// TimerRoute///////////////////////////////

    fromF("timer://simpleTimer?delay=2s").autoStartup(true).log("done,success");

    ////////////////////////// FileHandlingRoute////////////////////

    from("file:C:\\Users\\ESOMSIV\\Downloads\\pocc\\Testing\\input?noop=true")
        .process(new Processor() {

          @Override
          public void process(Exchange exchange) throws Exception {
            // TODO Auto-generated method stub
            System.out.println("content of the file " + exchange.getIn().getBody(String.class));
          }
        }).to("file:C:\\Users\\ESOMSIV\\Downloads\\pocc\\Testing\\output");

    //////////////////////////// RestRoute/////////////////////////

    restConfiguration().bindingMode(RestBindingMode.json);


    rest("/api/").get("/getin/").to("direct:testing");

    from("direct:testing").process(new Processor() {

      @Override
      public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody("HELLO", String.class);

      }
    }).log("IT seems working");

  }

}
