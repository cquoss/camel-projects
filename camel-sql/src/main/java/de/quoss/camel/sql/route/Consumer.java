package de.quoss.camel.sql.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

public class Consumer extends EndpointRouteBuilder {
    
    @Override
    public void configure() {
        
        from(sql("select * from foo"))
                .log("${body}");
        
    }
    
}
