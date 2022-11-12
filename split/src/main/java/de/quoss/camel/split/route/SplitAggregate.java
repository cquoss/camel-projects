package de.quoss.camel.split.route;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import java.util.ArrayList;
import java.util.List;

public class SplitAggregate extends EndpointRouteBuilder {
    
    static final String ROUTE_ID = "split-aggregate";
    
    @Override
    public void configure() {
        from(timer(ROUTE_ID).repeatCount(1L))
                .routeId(ROUTE_ID)
                .process(e -> {
                    final List<Integer> list = new ArrayList<>(1000); 
                    for (int i = 0; i < 1000; i++) {
                        list.add(i);
                    }
                    e.getMessage().setBody(list, ArrayList.class);
                })
                .split().body().parallelProcessing()
                    .aggregate(AggregationStrategies.flexible(Integer.class).accumulateInCollection(ArrayList.class)).constant(true).completionSize(5)
                    .log("${body}")
                .end();
    }
    
}
