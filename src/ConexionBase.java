import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 * @author AVillamil
 * @version 1.0
 */
public class ConexionBase {
    private static final String URL = "jdbc:mysql://localhost:3306/Futbolreserva";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    /**
     * Obtiene una conexión con la base de datos.
     *
     * @return un objeto {@link Connection} si la conexión es exitosa, de lo contrario, retorna {@code null}.
     */
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
