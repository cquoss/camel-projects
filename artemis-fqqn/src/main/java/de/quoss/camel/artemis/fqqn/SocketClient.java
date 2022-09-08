package de.quoss.camel.artemis.fqqn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.Socket;

public class SocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketClient.class);
    
    private void run() throws Exception {
        final String methodName = "run()";
        try (final Socket socket = new Socket("localhost", 61616)) {
            final InputStream inputStream = socket.getInputStream();
            if (inputStream == null) {
                LOGGER.info("{} input stream is null.", methodName);
            } else {
                LOGGER.info("{} input stream is not null.", methodName);
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        new SocketClient().run();
    }
}
