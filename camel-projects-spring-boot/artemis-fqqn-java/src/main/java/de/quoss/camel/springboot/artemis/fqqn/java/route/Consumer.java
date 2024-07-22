package de.quoss.camel.springboot.artemis.fqqn.java.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Consumer extends EndpointRouteBuilder {
    
    @Override
    public void configure() {
        
        from(jms("topic:foo::bar"))
                .log("From consumer.");
        
    }

}
