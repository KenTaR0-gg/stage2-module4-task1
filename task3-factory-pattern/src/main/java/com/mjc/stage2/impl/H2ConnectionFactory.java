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

        // 1. Указываем ПРАВИЛЬНОЕ имя файла
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("h2database.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Достаем настройки по ПРАВИЛЬНЫМ ключам из h2database.properties
        String url = props.getProperty("db_url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        // 3. Возвращаем соединение
        return DriverManager.getConnection(url, user, password);
    }
}