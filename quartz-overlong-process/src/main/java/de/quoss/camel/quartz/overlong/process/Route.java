package de.quoss.camel.quartz.overlong.process;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class Route extends EndpointRouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(Route.class);

    static final String ROUTE_ID = "route";

    @Override
    public void configure() {
        from(quartz("trigger")).routeId(ROUTE_ID)
                .process(e -> {
                    long l = ThreadLocalRandom.current().nextLong(60000L);
                    LOGGER.info("Sleeping for {} milliseconds.", l);
                    Thread.sleep(l);
                })
                .to(log(ROUTE_ID + ".end"));
    }

}
