import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa el formulario de inicio de sesión para el administrador.
 * Permite a los administradores autenticarse en la aplicación mediante su cédula
 * y contraseña. Proporciona opciones para iniciar sesión o regresar a la pantalla de inicio.
 */
public class loginadmin extends JFrame {
    private JPasswordField passAdmin;
    private JTextField cedulaAdmin;
    private JButton iniciarSesiónButton;
    private JButton regresarButton;
    private JPanel adminLogin;

    /**
     * Constructor que inicializa el formulario de inicio de sesión del administrador y configura los componentes de la interfaz.
     */
    public loginadmin() {
        setTitle("Iniciar Sesión");
        setSize(600, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(adminLogin);
        setLocationRelativeTo(null);

        iniciarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = cedulaAdmin.getText();
                String contrasena = new String(passAdmin.getPassword());

                if (usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (autenticarAdmin(usuario, contrasena)) {
                    new menuadmin().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Autentica al administrador en la base de datos.
     *
     * @param usuario    El nombre de usuario del administrador.
     * @param contrasena La contraseña del administrador.
     * @return true si las credenciales son válidas, false en caso contrario.
     * @author AVillamil
     * @version 1.0
     */
    private boolean autenticarAdmin(String usuario, String contrasena) {
        boolean autenticado = false;
        Connection connection = ConexionBase.getConnection();

        if (connection == null) {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión con la base de datos.");
            return false;
        }

        String query = "SELECT * FROM ADMIN WHERE usuario = ? AND contrasena = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contrasena);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                autenticado = true;
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error al autenticar el usuario.");
            e.printStackTrace();
        }

        return autenticado;
    }
}
