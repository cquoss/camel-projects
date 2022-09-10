package de.quoss.camel.split.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SplitArrayList extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "split-array-list";

    private static final Logger LOGGER = LoggerFactory.getLogger(SplitArrayList.class);
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String PROCESS_1_ID = ROUTE_ID + "-process-1";

    @Override
    public void configure() {
        from(timer(ROUTE_ID).repeatCount(1L))
                .routeId(ROUTE_ID)
                .process(e -> {
                    final List<Integer> list = new ArrayList<>(2);
                    list.add(1);
                    list.add(2);
                    e.getMessage().setBody(list);
                }).id(PROCESS_0_ID)
                .split().body()
                .process(e -> {
                    final Object o = e.getMessage().getBody();
                    LOGGER.info("{} [o={},o.class.name={}]", PROCESS_1_ID, o, o.getClass().getName());
                }).id(PROCESS_1_ID)
                .end();
    }
    
}
