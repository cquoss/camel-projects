package de.quoss.camel.artemis.fqqn;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class Consumer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "consumer";
    
    @Override
    public void configure() {
        from(jms("topic:foo::bar").connectionFactory(new ActiveMQConnectionFactory("tcp://localhost:61616", "admin", "admin")))
                .routeId(ROUTE_ID)
                .log("${exchangeId}");
    }
    
}
