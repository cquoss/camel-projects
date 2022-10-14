package de.quoss.camel.sql.stream.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.sql.SqlOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Stream extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "stream";
    
    private static final String PROCESS_0_ID = ROUTE_ID + "-process-0";

    private static final String PROCESS_1_ID = ROUTE_ID + "-process-1";

    private static final String SPLIT_0_ID = ROUTE_ID + "-split-0";

    private static final Logger LOGGER = LoggerFactory.getLogger(Stream.class);
    
    @Override
    public void configure() {
        
        from(timer("camel-sql-stream/" + ROUTE_ID).period("5000L"))
            .routeId(ROUTE_ID)
            .log("Start")
            .to(sql("select * from foo").outputType(SqlOutputType.StreamList))
            .split(body()).streaming()
                .process(exchange -> {
                    Object o = exchange.getMessage().getBody();
                    LOGGER.info("{} [o={},o.class.name={}]", PROCESS_0_ID, o, o == null ? "null" : o.getClass().getName());
                })
                .id(PROCESS_0_ID)
            .end()
            .log("End");
        
    }
    
}
