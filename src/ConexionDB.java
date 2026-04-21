import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    public static Connection getConexion() throws Exception {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String user = "system";
        String pass = "Tapiero123";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(url, user, pass);
    }
}   
