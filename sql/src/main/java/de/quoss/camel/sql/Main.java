package de.quoss.camel.sql;

import de.quoss.camel.sql.route.RawConsumer;
import de.quoss.camel.sql.route.TimedConsumer;
import de.quoss.camel.sql.route.TypedConsumer;
import de.quoss.camel.sql.util.CustomTracer;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private void run() throws Exception {
        DataSource ds = createDataSource();
        executeSql(ds, "create.sql");
        executeSql(ds, "insert.sql");
        try (final CamelContext context = new DefaultCamelContext()) {
            context.setTracer(new CustomTracer());
            context.setTracing(true);
            context.setUseBreadcrumb(true);
            context.setMessageHistory(true);
            context.addRoutes(new RawConsumer());
            // context.addRoutes(new TimedConsumer());
            // context.addRoutes(new TypedConsumer());
            context.getRegistry().bind("dataSource", ds);
            context.start();
            Thread.sleep(300000L);
            context.stop();
        }
    }

    private DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
    
    private void executeSql(final DataSource ds, final String name) throws IOException, SQLException {
        try (final Connection c = ds.getConnection();
             final Statement s = c.createStatement()) {
            s.execute(getContent(name));
        }
        
    } 
    
    private String getContent(final String name) throws IOException {
        return Files.readString(getFile(name).toPath());
    }
    
    private File getFile(final String name) throws IOException {
        final ClassLoader cl = getClass().getClassLoader();
        final URL resource = cl.getResource(name);
        if (resource == null) {
            throw new IOException("File " + name + " not found in class path.");
        }
        return new File(resource.getFile());
    } 
    
    public static void main(final String[] args) throws Exception {
        new Main().run();
    }

}
