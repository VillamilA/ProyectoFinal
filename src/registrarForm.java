import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrarForm extends JFrame {
    private JPanel registro;
    private JTextField campoNuevoUsuario;
    private JButton registrarButton;
    private JButton regresarLoginButton;
    private JPasswordField campoNuevoContrasena;

    public registrarForm(){
    setTitle("Iniciar Sesión");
    setSize(500,600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(registro);
    setLocationRelativeTo(null);
    registrarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nuevoUsuario = campoNuevoUsuario.getText();
            String nuevaContrasena = new String(campoNuevoContrasena.getPassword());

            registrarUsuario(nuevoUsuario, nuevaContrasena);
        }
    });

    regresarLoginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        new login().setVisible(true);
        dispose();
        }
    });
}

    private void registrarUsuario(String usuario, String contrasena) {
        Connection connection = ConexionBase.getConnection();
        String query = "INSERT INTO USUARIO (username, password) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contrasena);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");
                new login().setVisible(true);
                dispose();
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
