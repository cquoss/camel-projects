package de.quoss.camel.artemis.fqqn;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.XMLRoutesDefinitionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainXml.class);

    private void run(final String[] args) throws Exception {
        final String methodName = "run(String[])";
        try (final DefaultCamelContext context = new DefaultCamelContext();
             final ProducerTemplate template = context.createProducerTemplate();
             final ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616")) {
            context.addRoutes(new Consumer());
            context.addRoutes(new Producer());
            context.start();
            final Exchange request = ExchangeBuilder.anExchange(context).build();
            final Exchange response = template.send("direct://producer", request);
            LOGGER.info("{} [response={}]", methodName, response);
            Thread.sleep(30000L);
            context.stop();
        }
    }

    public static void main(final String[] args)  throws Exception {
        new Main().run(args);
    }

}
