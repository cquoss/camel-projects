package de.quoss.camel.http.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.http.HttpComponent;

public class ToVoid extends EndpointRouteBuilder {

    static final String ROUTE_ID = "to-void";

    private static final String HTTP_0_ID = ROUTE_ID + "-http-0";

    @Override
    public void configure() {

        // configure http component - set socket timeout to 1 minute
        ((HttpComponent) getContext().getComponent("http")).setSocketTimeout(60000);
        
        from(timer(ROUTE_ID).repeatCount(1L))
                .routeId(ROUTE_ID)
                .to(http("localhost:53537"))
                .id(HTTP_0_ID)
                .log("Done.");

    }

}
