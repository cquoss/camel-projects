package de.quoss.camel.artemis.fqqn;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainXml {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainXml.class);
    
    private void run(final String[] args) throws Exception {
        final String methodName = "run(String[])";
        try (final ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("main.xml");
                final CamelContext context = springContext.getBean(CamelContext.class);
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
