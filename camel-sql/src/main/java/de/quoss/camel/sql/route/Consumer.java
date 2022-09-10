package de.quoss.camel.sql.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "consumer";
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String SPLIT_0_ID = ROUTE_ID + "-split-0";

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    
    @Override
    public void configure() {
        
        from(sql("select * from foo"))
                .routeId(ROUTE_ID)
                .split().body().id(SPLIT_0_ID)
                    .process(e -> {
                        Object o = e.getMessage().getBody();
                        LOGGER.info("{} [o={},o.class.name={}]", PROCESS_0_ID, o, o.getClass().getName());
                    }).id(PROCESS_0_ID)
                .end();
        
    }
    
}
