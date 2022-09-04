package de.quoss.camel.artemis.fqqn;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class Producer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "producer";
    
    @Override
    public void configure() {
        from(direct(ROUTE_ID))
                .routeId(ROUTE_ID)
                .to(jms("topic:foo::bar"));
    }
    
}
