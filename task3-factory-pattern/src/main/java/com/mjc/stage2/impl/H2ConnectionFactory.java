package com.mjc.stage2.impl;

import com.mjc.stage2.ConnectionFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2ConnectionFactory implements ConnectionFactory {

    @Override
    public Connection createConnection() throws SQLException {
        Properties props = new Properties();

        ClassLoader classLoader = H2ConnectionFactory.class.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }

        try (InputStream input = classLoader.getResourceAsStream("h2database.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String driver = props.getProperty("jdbc_driver");
        String url = props.getProperty("db_url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        try {
            if (driver != null && !driver.isEmpty()) {
                Class.forName(driver);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(url, user, password);
    }
}