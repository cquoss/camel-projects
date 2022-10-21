package de.quoss.camel.sql.stream.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.sql.SqlOutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Stream extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "stream";
    
    @Override
    public void configure() {
        
        from(direct(ROUTE_ID))
            .routeId(ROUTE_ID)
            .log("Start")
            .to(sql("select * from customer").outputType(SqlOutputType.StreamList))
            .split(body()).streaming()
                .process(exchange -> {
                    Thread.sleep(200L);
                })
            .end()
            .log("End");
        
    }
    
}
