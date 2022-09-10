package de.quoss.camel.springboot.artemis.fqqn.java.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Producer extends EndpointRouteBuilder {
    
    @Override
    public void configure() {
        from(timer("dummy").period(30000L))
                .log("From producer.")
                .toD(jms("topic:foo::bar"));
    }
    
}
