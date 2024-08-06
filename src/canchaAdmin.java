import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class canchaAdmin extends JFrame {
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTextField idcancha;
    private JTextField canchanombre;
    private JTextField ubicancha;
    private JButton agregarButton;
    private JTextField ideliminar;
    private JButton buscarEliminar;
    private JComboBox boxeliminar;
    private JButton eliminarButton;
    private JButton irMenúButton;
    private JPanel canchaadmin;

    public canchaAdmin() {
        setTitle("Canchas");
        setSize(550,425);
        setContentPane(canchaadmin);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idcancha.getText();
                String cancha = canchanombre.getText();
                String ubicacion = ubicancha.getText();

                crearCancha(id,cancha,ubicacion);
            }
        });
        irMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new menuadmin().setVisible(true);
                dispose();

            }
        });
        buscarEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCancha();
            }
        });
    }

    private void crearCancha(String id, String cancha, String ubicacion) {
        Connection connection = ConexionBase.getConnection();
        String query = "INSERT INTO canchas (id, nombre, ubicacion) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, cancha);
            preparedStatement.setString(3, ubicacion);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cancha registrada con éxito");
                return;
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void cargarCancha() {
        String idelimina = ideliminar.getText();
        if (idelimina.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo ID es obligatorio para cargar la cancha", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection connection = ConexionBase.getConnection();
        if (connection != null) {
            try {
                // Limpiar el JComboBox
                boxeliminar.removeAllItems();

                // Consultar reservas para la cédula proporcionada
                String query = "SELECT id, nombre, ubicacion FROM canchas WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, idelimina);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int idcanc = resultSet.getInt("ID");
                    String nombrecanc = resultSet.getString("nombre");
                    String ubican = resultSet.getString("ubicacion");
                    boxeliminar.addItem("ID: " + idcanc + " - Nombre: " + nombrecanc + " - Ubicado en " + ubican);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
