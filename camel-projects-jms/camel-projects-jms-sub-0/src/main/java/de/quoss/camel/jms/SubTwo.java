package de.quoss.camel.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class SubTwo extends EndpointRouteBuilder {
	
	private static final ActiveMQConnectionFactory FACTORY = new ActiveMQConnectionFactory("tcp://localhost:61616");
	
	@Override
	public void configure() {
		from(jms("topic:pub-1").subscriptionDurable(true).subscriptionShared(true).durableSubscriptionName("pub-1-sub-0").connectionFactory(FACTORY))
				.log("Message received?");
	}

}
