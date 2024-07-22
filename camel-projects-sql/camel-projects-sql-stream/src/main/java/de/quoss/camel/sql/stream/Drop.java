package de.quoss.camel.sql.stream;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Drop {

    private static final Logger LOGGER = LoggerFactory.getLogger(Drop.class);
    
    private void run() throws Exception {
        DataSource ds = createDataSource();
        LOGGER.info("Data source created.");
        executeSql(ds, "drop.sql");
        LOGGER.info("Tables dropped.");
    }

    private DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        // dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setURL("jdbc:h2:tcp://localhost/C:/Daten/h2/test");
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
        new Drop().run();
    }

}