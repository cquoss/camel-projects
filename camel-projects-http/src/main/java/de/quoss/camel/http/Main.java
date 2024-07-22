package de.quoss.camel.http;

import de.quoss.camel.http.route.ToVoid;
import de.quoss.camel.http.util.CustomTracer;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    private void run() throws Exception {
        try (final CamelContext context = new DefaultCamelContext()) {
            context.setTracer(new CustomTracer());
            context.setTracing(true);
            context.setUseBreadcrumb(true);
            context.setMessageHistory(true);
            context.addRoutes(new ToVoid());
            context.start();
            Thread.sleep(600000L);
            context.stop();
        }
    }

    public static void main(final String[] args) throws Exception {
        new Main().run();
    }

}
