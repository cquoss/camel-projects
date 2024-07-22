package de.quoss.camel.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    private void run() throws Exception {
        try (final CamelContext context = new DefaultCamelContext()) {
            context.setTracing(true);
            context.setUseBreadcrumb(true);
            context.setMessageHistory(true);
            context.addRoutes(new SubZero());
            context.start();
            Thread.sleep(600000L);
            context.stop();
        }
    }

    public static void main(final String[] args) throws Exception {
        new Main().run();
    }

}
