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

        // Читаем настройки из файла
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            System.out.println("Не удалось прочитать файл настроек");
        }

        String url = props.getProperty("h2.url");
        String user = props.getProperty("h2.user");
        String password = props.getProperty("h2.password");

        return DriverManager.getConnection(url, user, password);
    }
}

