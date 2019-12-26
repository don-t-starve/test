package util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBUtil {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    static {
        try {
            Properties properties = new Properties();
            FileInputStream inputStream = new FileInputStream(new File("src\\main\\java\\config\\db.properties"));
            properties.load(inputStream);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConn() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
