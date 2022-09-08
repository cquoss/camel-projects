package de.quoss.camel.artemis.fqqn;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;

public class Server {

    private static final EmbeddedActiveMQ EMBEDDED_ACTIVE_MQ = new EmbeddedActiveMQ();
    
    private void run() throws Exception {
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        System.setProperty("org.jboss.logging.provider", "slf4j");
        EMBEDDED_ACTIVE_MQ.start();
        Thread.sleep(600000L);
        EMBEDDED_ACTIVE_MQ.stop();
    }
    
    public static void main(String[] args) throws Exception {
        new Server().run();
    }
    
}
