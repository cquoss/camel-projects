package de.quoss.camel.split.aggregate;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    private void run() throws Exception {
        try (final DefaultCamelContext context = new DefaultCamelContext();
             final ProducerTemplate template = context.createProducerTemplate()) {
            context.addRoutes(new Route());
            context.start();
            final Exchange exchange = ExchangeBuilder.anExchange(context).build();
            final Exchange received = template.send("direct://" + Route.ROUTE_ID, exchange);
            LOGGER.info("Failed: {}", received.isFailed());
            // Thread.sleep(5000L);
            context.stop();
        }
    }
    
    public static void main(final String[] args) throws Exception {
        new Main().run();
    }
    
}
