package de.quoss.camel.quarkus.artemis.fqqn;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "main-route-config")
public interface MainRouteConfig {
    String acknowledgementModeName();
    int receiveTimeout();
}
