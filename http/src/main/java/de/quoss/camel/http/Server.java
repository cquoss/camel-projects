package de.quoss.camel.http;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Server {

    private void run() throws Exception {
        final InetSocketAddress address = new InetSocketAddress(53537);
        final HttpServer server = HttpServer.create(address, 10);
        final HttpContext context = server.createContext("/", exchange -> {
            // sleep 5 minutes
            try {
                Thread.sleep(300000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            exchange.close();
        });
        server.start();
    }
    
    public static void main(final String[] args) throws Exception {
        new Server().run();
    }
    
}
