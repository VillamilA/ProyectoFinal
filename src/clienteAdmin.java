import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class clienteAdmin extends JFrame {

    private JTabbedPane tabbedPane1;
    private JTextField campoCedula;
    private JTextField campoContra;
    private JTextField campoUsuario;
    private JPanel clientemenu;
    private JButton crearClienteButton;
    private JTabbedPane tabbedPane2;

public clienteAdmin(){
    setTitle("Clientes");
    setSize(550,425);
    setContentPane(clientemenu);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    crearClienteButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cedula = campoCedula.getText();
            String usuario = campoUsuario.getText();
            String contrasena = campoContra.getText();

            crearCliente(cedula,usuario,contrasena);
        }
    });
}
    private void crearCliente(String cedula, String usuario, String contrasena) {
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
                return;
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
