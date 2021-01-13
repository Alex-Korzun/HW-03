package manufacturing.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection(){
        Properties dbProps = new Properties();
        dbProps.put("user", "root");
        dbProps.put("password", "1234");
        String url = "jdbc:mysql://localhost:3306/manufacturing";
        try {
            return DriverManager.getConnection(url, dbProps);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish the connection to DB", e);
        }
    }
}
