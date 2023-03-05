package de.quoss.camel.http;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class); 
    
    private void run() throws Exception {
        final String methodName = "run()";
        final InetSocketAddress address = new InetSocketAddress(53537);
        final HttpServer server = HttpServer.create(address, 10);
        final HttpContext context = server.createContext("/", exchange -> {
            LOGGER.info("{} [exchange={}]", methodName, exchange);
            // exchange.
            // sleep 5 minutes
            try {
                Thread.sleep(300000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            exchange.close();
        });
        LOGGER.info("{} [context={}]", methodName, context);
        server.start();
    }
    
    public static void main(final String[] args) throws Exception {
        new Server().run();
    }
    
}
