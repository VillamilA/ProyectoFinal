import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class clienteAdmin extends JFrame {

    private JTabbedPane tabbedPane1;
    private JTextField campoCedula;
    private JTextField campoContra;
    private JTextField campoUsuario;
    private JPanel clientemenu;
    private JButton crearClienteButton;
    private JTabbedPane tabbedPane2;
    private JTextField campCedula;
    private JComboBox boxcliente;
    private JButton buscarButton;
    private JButton eliminarButton;

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
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReservas();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clienteBuscado = (String) boxcliente.getSelectedItem();

                if (clienteBuscado == null || clienteBuscado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una reserva para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la opción seleccionada?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    String[] partes = clienteBuscado.split(" - ");
                    int cedulaCliente = Integer.parseInt(partes[0]);

                    Connection connection = ConexionBase.getConnection();
                    if (connection != null) {
                        try {
                            String deleteQuery = "DELETE FROM USUARIO WHERE cedula = ?";
                            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                            deleteStatement.setInt(1, cedulaCliente);
                            int rowsAffected = deleteStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito");
                                cargarReservas(); // Recargar las reservas
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo eliminar el Usuario", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            deleteStatement.close();
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
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

    private void cargarReservas() {
        String cedula = campCedula.getText();
        if (cedula.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo cédula es obligatorio para cargar reservas", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection connection = ConexionBase.getConnection();
        if (connection != null) {
            try {
                // Limpiar el JComboBox
                boxcliente.removeAllItems();

                // Consultar reservas para la cédula proporcionada
                String query = "SELECT cedula, usuario, contrasena FROM USUARIO WHERE cedula = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, cedula);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int ced = resultSet.getInt("cedula");
                    String Usuario = resultSet.getString("usuario");
                    String Contra = resultSet.getString("contrasena");
                    boxcliente.addItem(ced + " - Usuario: " + Usuario + " - Contraseña " + Contra);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


}
