import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class consultaForm extends JFrame {
    private JTextField campoCedula;
    private JButton buscarButton;
    private JTextArea text;
    private JButton menúButton;
    private JPanel consulta;

    public consultaForm() {
        setTitle("Consulta de Reserva");
        setSize(500, 600);
        setContentPane(consulta);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = campoCedula.getText();
                if (cedula.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La cédula es obligatoria", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Connection connection = ConexionBase.getConnection();
                if (connection != null) {
                    try {
                        String query = "SELECT * FROM reserva WHERE cedula = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, cedula);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        text.setText("");

                        if (!resultSet.isBeforeFirst()) { // Verificar si el ResultSet está vacío
                            text.setText("USUARIO NO TIENE NINGUNA RESERVA");
                        } else {
                            text.append("La información de tu reserva realizada es:\n");
                            while (resultSet.next()) {
                                text.append("ID: " + resultSet.getInt("id") + "\n");
                                text.append("Cédula: " + resultSet.getString("cedula") + "\n");
                                text.append("Fecha: " + resultSet.getDate("fecha") + "\n");
                                text.append("Hora: " + resultSet.getTime("hora") + "\n");
                                text.append("Cancha: " + resultSet.getString("cancha") + "\n");
                                text.append("-----------------------\n");
                            }
                            text.append("Recuerda que está PROHIBIDO el consumo de bebidas alcohólicas.");
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

        menúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new menu().setVisible(true);
                dispose();
            }
        });
    }
}
