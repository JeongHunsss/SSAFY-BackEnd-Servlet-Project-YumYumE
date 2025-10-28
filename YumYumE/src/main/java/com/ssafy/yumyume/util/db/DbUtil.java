package com.ssafy.yumyume.util.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    public static String GEMINI_API_KEY;

    static {
        try {
            Properties prop = new Properties();
            // resources 폴더에서 읽기
            InputStream is = DbUtil.class.getClassLoader()
                .getResourceAsStream("config.properties");
            prop.load(is);

            DRIVER = prop.getProperty("db.driver");
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.username");
            PASSWORD = prop.getProperty("db.password");
            
            GEMINI_API_KEY = prop.getProperty("gemini.api.key");

            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DbUtil() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(AutoCloseable... closables) {
        for(AutoCloseable closable : closables) {
            try {
                if(closable != null)
                    closable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}