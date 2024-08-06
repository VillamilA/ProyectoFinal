import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reservarForm extends JFrame {
    private JTextField campoCedula;
    private JTextField campoFecha;
    private JTextField campoHora;
    private JTextField campoCancha;
    private JButton reservarButton;
    private JPanel reservarPanel;
    private JButton menubutton;

    public reservarForm() {
        setTitle("Reservar Cancha");
        setSize(500, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(reservarPanel);
        setLocationRelativeTo(null);

        // Ajustar la longitud de los JTextField
        campoCedula.setColumns(10);
        campoFecha.setColumns(10);
        campoHora.setColumns(10);
        campoCancha.setColumns(10);

        reservarButton.addActionListener(new ActionListener() {
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
                        // Verificar si ya existe una reserva para la misma persona en el mismo día
                        String checkQuery = "SELECT * FROM reserva WHERE cedula = ? AND fecha = ?";
                        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
                        checkStatement.setString(1, cedula);
                        checkStatement.setString(2, fecha);
                        ResultSet resultSet = checkStatement.executeQuery();

                        if (resultSet.next()) {
                            JOptionPane.showMessageDialog(null, "Ya tiene una reserva para la fecha seleccionada", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        } else {
                            // Verificar si ya existe una reserva para la misma fecha, hora y cancha
                            String checkReservaQuery = "SELECT * FROM reserva WHERE fecha = ? AND hora = ? AND cancha = ?";
                            PreparedStatement checkReservaStatement = connection.prepareStatement(checkReservaQuery);
                            checkReservaStatement.setString(1, fecha);
                            checkReservaStatement.setString(2, hora);
                            checkReservaStatement.setString(3, cancha);
                            ResultSet resultSetReserva = checkReservaStatement.executeQuery();

                            if (resultSetReserva.next()) {
                                JOptionPane.showMessageDialog(null, "La cancha ya está reservada para la fecha y hora seleccionadas", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            } else {
                                // Insertar nueva reserva
                                String insertQuery = "INSERT INTO reserva (cedula, fecha, hora, cancha) VALUES (?, ?, ?, ?)";
                                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                                insertStatement.setString(1, cedula);
                                insertStatement.setString(2, fecha);
                                insertStatement.setString(3, hora);
                                insertStatement.setString(4, cancha);
                                insertStatement.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Reserva realizada con éxito");
                                insertStatement.close();
                            }

                            resultSetReserva.close();
                            checkReservaStatement.close();
                        }

                        resultSet.close();
                        checkStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
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
}