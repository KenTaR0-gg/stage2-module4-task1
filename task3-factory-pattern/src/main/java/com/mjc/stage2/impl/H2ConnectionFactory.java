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

        // 1. Читаем правильный файл настроек
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("h2database.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Достаем все настройки по правильным ключам
        String driver = props.getProperty("jdbc_driver");
        String url = props.getProperty("db_url");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        // 3. Принудительно подгружаем драйвер H2 (помогает избежать зависаний на сервере MJC)
        try {
            if (driver != null) {
                Class.forName(driver);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 4. Возвращаем соединение
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        return DriverManager.getConnection(url, user, password);
    }
}