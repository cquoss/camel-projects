package de.quoss.camel.artemis.fqqn;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.spi.XMLRoutesDefinitionLoader;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.Collection;

public class MainXml {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainXml.class);
    
    private void run(final String[] args) throws Exception {
        final String methodName = "run(String[])";
        final ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("main.xml");
        try (final CamelContext context = springContext.getBean(CamelContext.class);
             final ProducerTemplate template = context.createProducerTemplate()) {
            context.start();
            Thread.sleep(10000L);
            final Exchange request = ExchangeBuilder.anExchange(context).build();
            final Exchange response = template.send("direct://producer-xml", request);
            LOGGER.info("{} [response={}]", methodName, response);
            Thread.sleep(10000L);
            context.stop();
        }        
    }
    
    public static void main(final String[] args)  throws Exception {
        new MainXml().run(args);
    }
    
}
