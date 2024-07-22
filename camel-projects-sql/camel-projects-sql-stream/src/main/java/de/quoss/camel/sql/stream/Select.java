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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Select {

    private static final Logger LOGGER = LoggerFactory.getLogger(Select.class);
    
    private void run() throws Exception {
        DataSource ds = createDataSource();
        LOGGER.info("Data source created.");
        Object o = executeSql(ds, "select.sql");
        if (o instanceof Integer) {
            LOGGER.info("Update count: {}", o);
        } else {
            LOGGER.info("Result: {}", o);
        }
        LOGGER.info("Data selected.");
    }

    private DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        // dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setURL("jdbc:h2:tcp://localhost/C:/Daten/h2/test");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
    
    private Object executeSql(final DataSource ds, final String name) throws IOException, SQLException {
        try (final Connection c = ds.getConnection();
             final Statement s = c.createStatement()) {
            if (s.execute(getContent(name))) {
                final List<Map<String, Object>> result = new LinkedList<>(); 
                final ResultSet rs = s.getResultSet();
                final ResultSetMetaData rsmd = rs.getMetaData();
                while (rs.next()) {
                    final Map<String, Object> row = new HashMap<>();
                    result.add(row);
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        row.put(rsmd.getColumnName(i), rs.getObject(i));
                    }
                }
                return result;
            } else {
                return s.getUpdateCount();
            }
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
        new Select().run();
    }

}
