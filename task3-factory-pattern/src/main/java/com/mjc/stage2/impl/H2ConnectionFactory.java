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

        // Попробуй поменять на "application.properties", если ошибка сохранится
        String fileName = "app.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                // Теперь тест упадет с понятной надписью, если имя файла неверное
                throw new RuntimeException("Файл " + fileName + " не найден в папке resources! Проверь его точное название.");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }

        String url = props.getProperty("h2.url");
        String user = props.getProperty("h2.user");
        String password = props.getProperty("h2.password");

        if (url == null) {
            // Тест подскажет, если ключи в файле называются иначе (например jdbc.url)
            throw new RuntimeException("Ключ 'h2.url' не найден! Открой файл " + fileName + " и проверь, как точно называются ключи.");
        }

        return DriverManager.getConnection(url, user, password);
    }
}