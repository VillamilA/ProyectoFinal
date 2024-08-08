import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa la interfaz de inicio de sesión del sistema.
 */
public class login extends JFrame {
    private JTextField campoUsu;
    private JPasswordField campoContra;
    private JButton ingresarButton;
    private JButton registrarmeButton;
    private JPanel logeo;
    private JButton pulsaAquíButton;

    /**
     * Constructor que inicializa la interfaz gráfica de inicio de sesión.
     */
    public login() {
        setTitle("Iniciar Sesión");
        setSize(600, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(logeo);

        setLocationRelativeTo(null);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsu.getText();
                String contrasena = new String(campoContra.getPassword());

                if (usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (autenticarUsuario(usuario, contrasena)) {
                    new menu().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        });

        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new registrarForm().setVisible(true);
                dispose();
            }
        });

        pulsaAquíButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loginadmin().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Autentica un usuario verificando su nombre de usuario y contraseña en la base de datos.
     *
     * @param usuario    El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     * @return true si las credenciales son correctas, false de lo contrario.
     */
    private boolean autenticarUsuario(String usuario, String contrasena) {
        boolean autenticado = false;
        Connection connection = ConexionBase.getConnection();

        if (connection == null) {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión con la base de datos.");
            return false;
        }

        String query = "SELECT * FROM USUARIO WHERE usuario = ? AND contrasena = ?";

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

    /**
     * Método principal que lanza la aplicación de inicio de sesión.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        new login().setVisible(true);
    }
}
