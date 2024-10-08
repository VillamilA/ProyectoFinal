import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa el formulario de administración de clientes.
 * Permite al administrador crear, buscar, actualizar y eliminar clientes en la base de datos.
 *  @author AVillamil
 *  @version 1.0
 */
public class clienteAdmin extends JFrame {

    private JTextField campoCedula;
    private JTextField campoContra;
    private JTextField campoUsuario;
    private JPanel clientemenu;
    private JButton crearClienteButton;
    private JTabbedPane tabbedPane2;
    private JTextField campCedula;
    private JComboBox<String> boxcliente;
    private JButton buscarButton;
    private JButton eliminarButton;
    private JTabbedPane tabbedPane3;
    private JPanel elimina;
    private JPanel actualiza;
    private JTextField campCed;
    private JButton actualizarClienteButton;
    private JTextField campUsu;
    private JTextField campContr;
    private JTabbedPane tabbedPane1;
    private JTextField verCedula;
    private JButton buscarButton1;
    private JTextArea infotexto;
    private JButton regresarMenúButton;

    /**
     * Constructor que inicializa el formulario de administración de clientes y configura los componentes de la interfaz.
     */
    public clienteAdmin() {
        setTitle("Clientes");
        setSize(550, 425);
        setContentPane(clientemenu);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                String usuario = campoUsuario.getText();
                String contrasena = campoContra.getText();
                crearCliente(cedula, usuario, contrasena);
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

        actualizarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campCed.getText();
                String usuario = campUsu.getText();
                String contrasena = campContr.getText();
                if (cedula.trim().isEmpty() || usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Connection connection = ConexionBase.getConnection();
                if (connection != null) {
                    try {
                        String query = "UPDATE USUARIO SET usuario = ?, contrasena = ? WHERE cedula = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, usuario);
                        preparedStatement.setString(2, contrasena);
                        preparedStatement.setString(3, cedula);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con la cédula proporcionada");
                        }

                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        buscarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = verCedula.getText();
                if (cedula.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La cédula es obligatoria", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Connection connection = ConexionBase.getConnection();
                if (connection != null) {
                    try {
                        String query = "SELECT * FROM USUARIO WHERE cedula = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, cedula);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        infotexto.setText("");
                        if (!resultSet.isBeforeFirst()) { // Verificar si el ResultSet está vacío
                            infotexto.setText("USUARIO NO EXISTENTE");
                        } else {
                            infotexto.append("BIENVENIDO SR. ADMINISTRADOR :\n");
                            while (resultSet.next()) {
                                infotexto.append("La información de tu consulta realizada es:" + "\n");
                                infotexto.append("Cédula: " + resultSet.getString("cedula") + "\n");
                                infotexto.append("Usuario: " + resultSet.getString("usuario") + "\n");
                                infotexto.append("Contraseña: " + resultSet.getString("contrasena") + "\n");
                                infotexto.append("-----------------------\n");
                            }
                        }
                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        regresarMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new menuadmin().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param cedula    La cédula del cliente.
     * @param usuario   El nombre de usuario del cliente.
     * @param contrasena La contraseña del cliente.
     */
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
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga y muestra los clientes correspondientes a la cédula proporcionada en el JComboBox.
     */
    private void cargarReservas() {
        String cedula = campCedula.getText();
        if (cedula.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo cédula es obligatorio para cargar reservas", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection connection = ConexionBase.getConnection();
        if (connection != null) {
            try {
                boxcliente.removeAllItems();

                String query = "SELECT cedula, usuario, contrasena FROM USUARIO WHERE cedula = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, cedula);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int ced = resultSet.getInt("cedula");
                    String usuario = resultSet.getString("usuario");
                    String contrasena = resultSet.getString("contrasena");
                    boxcliente.addItem(ced + " - Usuario: " + usuario + " - Contraseña: " + contrasena);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
