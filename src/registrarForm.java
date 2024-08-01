import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrarForm extends JFrame {
    private JPanel registro;
    private JTextField campoCedula;
    private JButton registrarButton;
    private JButton regresarLoginButton;
    private JPasswordField campoNuevoContrasena;
    private JTextField campoUsuario;

    public registrarForm(){
    setTitle("Iniciar Sesión");
    setSize(500,600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(registro);
    setLocationRelativeTo(null);
    registrarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Cedula = campoCedula.getText();
            String Usuario = campoUsuario.getText();
            String nuevaContrasena = new String(campoNuevoContrasena.getPassword());

            registrarUsuario(Cedula,Usuario, nuevaContrasena);
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

    private void registrarUsuario(String cedula, String usuario, String contrasena) {
        Connection connection = ConexionBase.getConnection();
        String query = "INSERT INTO USUARIO (cedula, usuario, contrasena) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cedula);
            preparedStatement.setString(2, usuario);
            preparedStatement.setString(3, contrasena);

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
