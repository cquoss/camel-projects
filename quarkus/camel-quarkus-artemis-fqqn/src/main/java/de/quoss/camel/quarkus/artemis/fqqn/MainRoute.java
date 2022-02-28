package de.quoss.camel.quarkus.artemis.fqqn;

import io.quarkus.logging.Log;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.util.ObjectHelper;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Route consuming from shared durable subscription and inserting into db table (depending on body of jms message).
 * No other processors are involved (jms -> jdbc). Set up everything accordingly.
 * This route is configured with xa transactions enabled.
 */
@ApplicationScoped
public class MainRoute extends RouteBuilder {

    private static final ActiveMQXAConnectionFactory CONNECTION_FACTORY = new ActiveMQXAConnectionFactory();

    private final UserTransaction transaction;

    private final TransactionManager transactionManager;

    private final MainRouteConfig config;

    public MainRoute(final UserTransaction transaction, final TransactionManager transactionManager, final MainRouteConfig config) {
        this.transaction = ObjectHelper.notNull(transaction, "User transaction");
        this.transactionManager = ObjectHelper.notNull(transactionManager, "Transaction manger");
        this.config = ObjectHelper.notNull(config, "Configuration");
    }

    @Override
    public void configure() {

        final String methodName = "configure()";

        ((JmsComponent) getCamelContext().getComponent("jms")).setConnectionFactory(CONNECTION_FACTORY);

        ((JmsComponent) getCamelContext().getComponent("jms")).setTransactionManager(
                new JtaTransactionManager(transaction, transactionManager));

        String jmsUri = String.format("jms:topic:foo::bar?receiveTimeout=%s&acknowledgementModeName=%s", config.receiveTimeout(), config.acknowledgementModeName());

        Log.debugf("%s [jmsUri=%s]", methodName, jmsUri);

        from(jmsUri)
                .to("jdbc:default?resetAutoCommit=false");
    }

}
