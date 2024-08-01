import java.io.IOException;
import java.net.Socket;

public class VerificarConexion {
    public static void main(String[] args) {
        String host = "brfynjqqsudv99xcu1i4-mysql.services.clever-cloud.com";
        int port = 3306;
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Conexión al puerto 3306 exitosa!");
        } catch (IOException e) {
            System.out.println("No se pudo conectar al puerto 3306. Verifica tu firewall y configuración de red.");
            e.printStackTrace();
        }
    }
}
