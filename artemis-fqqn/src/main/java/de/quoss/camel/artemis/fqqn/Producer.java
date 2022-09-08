package de.quoss.camel.artemis.fqqn;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class Producer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "producer";
    
    @Override
    public void configure() {
        from(direct(ROUTE_ID))
                .routeId(ROUTE_ID)
                .to(jms("topic:foo::bar").connectionFactory(new ActiveMQConnectionFactory("tcp://localhost:61616")));
    }
    
}
