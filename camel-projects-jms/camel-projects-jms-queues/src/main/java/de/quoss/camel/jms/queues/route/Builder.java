package de.quoss.camel.jms.queues.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class Builder extends EndpointRouteBuilder {
    
    @Override
    public void configure() {
        from(direct("direct-0"))
        .to(jms("queue:queue-0"));
    }
    
}
