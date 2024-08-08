import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa la interfaz para eliminar reservas en el sistema.
 */
public class eliminarForm extends JFrame {
    private JTextField campoCedula;
    private JComboBox<String> comboReservas;
    private JButton buscarButton;
    private JButton eliminarButton;
    private JPanel eliminarPanel;
    private JButton menubutton;

    /**
     * Constructor que inicializa la interfaz gráfica para eliminar reservas.
     */
    public eliminarForm() {
        setTitle("Eliminar Reserva");
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(eliminarPanel);
        setLocationRelativeTo(null);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReservas();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reservaSeleccionada = (String) comboReservas.getSelectedItem();

                if (reservaSeleccionada == null || reservaSeleccionada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una reserva para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la reserva seleccionada?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    String[] partes = reservaSeleccionada.split(" - ");
                    int idReserva = Integer.parseInt(partes[0]);

                    Connection connection = ConexionBase.getConnection();
                    if (connection != null) {
                        try {
                            // Eliminar reserva
                            String deleteQuery = "DELETE FROM reserva WHERE id = ?";
                            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                            deleteStatement.setInt(1, idReserva);
                            int rowsAffected = deleteStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Reserva eliminada con éxito");
                                cargarReservas(); // Recargar las reservas
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo eliminar la reserva", "Error", JOptionPane.ERROR_MESSAGE);
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
        menubutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new menu().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Carga las reservas asociadas a la cédula proporcionada en el campo de texto.
     * Las reservas se muestran en un JComboBox para que el usuario pueda seleccionar y eliminar una.
     */
    private void cargarReservas() {
        String cedula = campoCedula.getText();
        if (cedula.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo cédula es obligatorio para cargar reservas", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection connection = ConexionBase.getConnection();
        if (connection != null) {
            try {
                // Limpiar el JComboBox
                comboReservas.removeAllItems();

                // Consultar reservas para la cédula proporcionada
                String query = "SELECT id, fecha, hora, cancha FROM reserva WHERE cedula = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, cedula);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String fecha = resultSet.getString("fecha");
                    String hora = resultSet.getString("hora");
                    String cancha = resultSet.getString("cancha");
                    comboReservas.addItem(id + " - " + fecha + " " + hora + " - " + cancha);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
