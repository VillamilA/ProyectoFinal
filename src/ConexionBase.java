import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBase {
    private static final String URL = "jdbc:mysql://localhost:3306/Futbolreserva";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa!");
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión con la base de datos.");
            e.printStackTrace();
        }
        return connection;
    }
}