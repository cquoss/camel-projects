package de.quoss.camel.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class SubZero extends EndpointRouteBuilder {
	
	private static final ActiveMQConnectionFactory FACTORY = new ActiveMQConnectionFactory("tcp://localhost:61616");
	
	@Override
	public void configure() {
		from(jms("topic:pub-0::sub-0").subscriptionDurable(true).durableSubscriptionName("sub-0").connectionFactory(FACTORY).clientId("client-0"))
				.log("Message received?");
	}

}
