package de.quoss.camel.split;

import de.quoss.camel.split.route.SplitAggregate;
import de.quoss.camel.split.route.SplitArrayList;
import de.quoss.camel.split.route.SplitHashMap;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    private void run() throws Exception {
        try (final CamelContext context = new DefaultCamelContext()) {
            // context.addRoutes(new SplitAggregate());
            // context.addRoutes(new SplitArrayList());
            context.addRoutes(new SplitHashMap());
            context.start();
            Thread.sleep(300000L);
            context.stop();
        }
    }
    
    public static void main(final String[] args) throws Exception {
        new Main().run();
    }
    
}
