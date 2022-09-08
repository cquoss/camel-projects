package de.quoss.camel.quartz.overlong.process;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {

    private void run(final String[] args) {
        // check command line (no args allowed)
        if (args.length == 0) {
            // OK. Do nothing.
        } else {
            throw new QuartzOverlongProcessException("USAGE: java " + getClass().getName());
        }
        // create camel context
        CamelContext context = new DefaultCamelContext();
        // register quartz component
        ComponentsBuilderFactory.quartz().register(context, "quartz");
        // register route
        try {
            context.addRoutes(new Route());
        } catch (Exception e) {
            throw new QuartzOverlongProcessException("Error adding route '" + Route.ROUTE_ID + "'.", e);
        }
        context.start();

    }

    public static void main(final String[] args) {
        new Main().run(args);
    }

}
