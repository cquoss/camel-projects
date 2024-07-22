package de.quoss.camel.sql.route;

import de.quoss.camel.sql.bean.Foo;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypedConsumer extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "typed-consumer";
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String PROCESS_1_ID = ROUTE_ID + "-process-1";

    private static final String SPLIT_0_ID = ROUTE_ID + "-split-0";

    private static final Logger LOGGER = LoggerFactory.getLogger(TypedConsumer.class);
    
    @Override
    public void configure() {
        
        from(sql("select * from foo")
                .outputClass(Foo.class.getName()))
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
                        if (o instanceof Foo) {
                            LOGGER.info("{} Exchange message body is a foo bean.", PROCESS_1_ID);
                            Foo foo = (Foo) o;
                            LOGGER.info("{} [foo={}]", PROCESS_1_ID, foo);
                        }
                    }).id(PROCESS_1_ID)
                .end();
        
    }
    
}
