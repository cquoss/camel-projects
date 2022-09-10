package de.quoss.camel.sql.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RawConsumer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "raw-consumer";
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String PROCESS_1_ID = ROUTE_ID + "-process-1";

    private static final String SPLIT_0_ID = ROUTE_ID + "-split-0";

    private static final Logger LOGGER = LoggerFactory.getLogger(RawConsumer.class);
    
    @Override
    public void configure() {
        
        from(sql("select * from foo"))
                .routeId(ROUTE_ID)
                .process(exchange -> {
                    Object o = exchange.getMessage().getBody();
                    LOGGER.info("{} [o={},o.class.name={}]", PROCESS_0_ID, o, o == null ? "null" : o.getClass().getName());
                })
                .id(PROCESS_0_ID)
                .split().body().id(SPLIT_0_ID)
                    .process(exchange -> {
                        final Object o = exchange.getMessage().getBody();
                        LOGGER.info("{} [o={},o.class.name={}]", PROCESS_1_ID, o, o == null ? "null" : o.getClass().getName());
                        if (o instanceof Map) {
                            LOGGER.info("{} Exchange message body is a map.", PROCESS_1_ID);
                            Map<?, ?> map = (Map) o;
                            for (Map.Entry<?, ?> entry : map.entrySet()) {
                                LOGGER.info("{} [entry.key={},entry.key.class.name={}]", PROCESS_1_ID, entry.getKey(), entry.getKey().getClass().getName());
                                LOGGER.info("{} [entry.value={},entry.value.class.name={}]", PROCESS_1_ID, entry.getValue(), entry.getValue().getClass().getName());
                            }
                        }
                        if (o instanceof Map.Entry) {
                            LOGGER.info("{} Exchange message body is a map entry.", PROCESS_1_ID);
                            Map.Entry<?, ?> entry = (Map.Entry) o;
                            LOGGER.info("{} [entry.key={},entry.key.class.name={}]", PROCESS_1_ID, entry.getKey(), entry.getKey().getClass().getName());
                            LOGGER.info("{} [entry.value={},entry.value.class.name={}]", PROCESS_1_ID, entry.getValue(), entry.getValue().getClass().getName());
                        }
                    }).id(PROCESS_1_ID)
                .end();
        
    }
    
}
