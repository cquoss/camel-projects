package de.quoss.camel.artemis.fqqn;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    private void run(final String[] args) throws Exception {
        try (final DefaultCamelContext context = new DefaultCamelContext();
             final ProducerTemplate template = context.createProducerTemplate()) { 
            context.addRoutes(new Consumer());
            context.addRoutes(new Producer());
            context.start();
            final Exchange exchange = ExchangeBuilder.anExchange(context).build();
            template.send("producer", exchange);
            Thread.sleep(500L);
            context.stop();
        }        
    }
    
    public static void main(final String[] args)  throws Exception {
        new Main().run(args);
    }
    
}
