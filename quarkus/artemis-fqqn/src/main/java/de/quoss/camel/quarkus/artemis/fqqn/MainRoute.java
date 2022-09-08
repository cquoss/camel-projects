package de.quoss.camel.quarkus.artemis.fqqn;

import de.unioninvestment.md.dp.basis.narayana.ConnectionFactoryProxy;
import de.unioninvestment.md.dp.basis.narayana.NarayanaTransactionHelper;
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

    public MainRoute(final UserTransaction transaction, final TransactionManager transactionManager) {
        this.transaction = ObjectHelper.notNull(transaction, "User transaction");
        this.transactionManager = ObjectHelper.notNull(transactionManager, "Transaction manger");
    }

    @Override
    public void configure() {

        CONNECTION_FACTORY.setClientID("client");

        ConnectionFactoryProxy proxy = new ConnectionFactoryProxy(CONNECTION_FACTORY, new NarayanaTransactionHelper(transactionManager));

        ((JmsComponent) getCamelContext().getComponent("jms")).setConnectionFactory(proxy);

        ((JmsComponent) getCamelContext().getComponent("jms")).setTransactionManager(
                new JtaTransactionManager(transaction, transactionManager));

        from("jms:topic:foo?receiveTimeout=30000&subscriptionName=bar&subscriptionDurable=true&subscriptionShared=true")
                .to("jdbc:default?resetAutoCommit=false");
    }

}
