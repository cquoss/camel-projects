package de.quoss.camel.sql.route;

import de.quoss.camel.sql.bean.Foo;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Consumer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "consumer";
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String SPLIT_0_ID = ROUTE_ID + "-split-0";

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    
    @Override
    public void configure() {
        
        from(sql("select * from foo").outputClass(Foo.class.getName()))
                .routeId(ROUTE_ID)
                .split().body().id(SPLIT_0_ID)
                    .process(e -> {
                        Object o = e.getMessage().getBody();
                        LOGGER.info("{} [o={},o.class.name={}]", PROCESS_0_ID, o, o == null ? "null" : o.getClass().getName());
                        Map.Entry<String, ?> entry = e.getMessage().getBody(Map.Entry.class);
                        LOGGER.info("{} [entry={},entry.class.name={}]", PROCESS_0_ID, entry, entry == null ? "null" : entry.getClass().getName());
                        final String key = entry == null ? "null" : entry.getKey();
                        final Object value = entry == null ? "null" : entry.getValue();
                        LOGGER.info("{} [key={},value={}]", PROCESS_0_ID, key, value);
                        Foo foo = e.getMessage().getBody(Foo.class);
                        LOGGER.info("{} [foo={}]", PROCESS_0_ID, foo);
                    }).id(PROCESS_0_ID)
                .end();
        
    }
    
}
