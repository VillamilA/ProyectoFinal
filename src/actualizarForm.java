import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class actualizarForm extends JFrame {
    private JTextField campoCedula;
    private JTextField campoFecha;
    private JTextField campoHora;
    private JTextField campoCancha;
    private JButton actualizarButton;
    private JPanel actualizarPanel;

    public actualizarForm() {
        setTitle("Actualizar Reserva");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(actualizarPanel);
        setLocationRelativeTo(null);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                String fecha = campoFecha.getText();
                String hora = campoHora.getText();
                String cancha = campoCancha.getText();

                if (cedula.trim().isEmpty() || fecha.trim().isEmpty() || hora.trim().isEmpty() || cancha.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Connection connection = ConexionBase.getConnection();
                if (connection != null) {
                    try {
                        String query = "update reserva set fecha = ?, hora = ?, cancha = ? where cedula = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, fecha);
                        preparedStatement.setString(2, hora);
                        preparedStatement.setString(3, cancha);
                        preparedStatement.setString(4, cedula);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Reserva actualizada con éxito");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ninguna reserva con la cédula proporcionada");
                        }
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}