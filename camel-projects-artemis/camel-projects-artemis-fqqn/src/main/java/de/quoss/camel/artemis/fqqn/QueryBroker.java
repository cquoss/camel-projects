package de.quoss.camel.artemis.fqqn;

import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientRequestor;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;
import org.apache.activemq.artemis.api.core.management.ManagementHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryBroker {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBroker.class);
    
    private void run() throws Exception {
        final String methodName = "run()";
        try (final ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616");
             final ClientSessionFactory factory = locator.createSessionFactory();
             final ClientSession session = factory.createSession()) {
            final ClientRequestor requestor = new ClientRequestor(session, "activemq.management");
            final ClientMessage request = session.createMessage(false);
            ManagementHelper.putAttribute(request, "broker", "name");
            session.start();
            final ClientMessage reply = requestor.request(request);
            final Object o = ManagementHelper.getResult(reply);
            LOGGER.info("{} [o={},o.class.name={}]", methodName, o, o.getClass().getName());
        }
    }
    
    public static void main(final String[] args) throws Exception {
        new QueryBroker().run();
    }
    
}
