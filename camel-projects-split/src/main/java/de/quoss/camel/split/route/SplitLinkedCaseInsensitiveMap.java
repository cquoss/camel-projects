package de.quoss.camel.split.route;

import java.util.Map;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class SplitLinkedCaseInsensitiveMap extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "split-linked-case-insensitive-map";

    private static final Logger LOGGER = LoggerFactory.getLogger(SplitLinkedCaseInsensitiveMap.class);
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String PROCESS_1_ID = ROUTE_ID + "-process-1";

    private static final String SPLIT_0_ID = ROUTE_ID + "-split-0";

    @Override
    public void configure() {
        from(timer(ROUTE_ID).repeatCount(1L))
                .routeId(ROUTE_ID)
                .process(e -> {
                    final LinkedCaseInsensitiveMap<Object> map = new LinkedCaseInsensitiveMap<>();
                    map.put("ID", Integer.parseInt("0"));
                    map.put("NAME", "Value 0");
                    e.getMessage().setBody(map);
                }).id(PROCESS_0_ID)
                .split().body().id(SPLIT_0_ID)
                .process(e -> {
                    final Object o = e.getMessage().getBody();
                    LOGGER.info("{} [o={},o.class.name={}]", PROCESS_1_ID, o, o.getClass().getName());
                }).id(PROCESS_1_ID)
                .end();
    }
    
}
