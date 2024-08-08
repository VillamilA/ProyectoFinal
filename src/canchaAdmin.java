import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa el formulario de administración de canchas.
 * Permite al administrador agregar, eliminar y actualizar canchas en la base de datos.
 * @author AVillamil
  *  @version 1.0
 */
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
    private JComboBox<String> boxeliminar;
    private JButton eliminarButton;
    private JButton irMenúButton;
    private JPanel canchaadmin;
    private JTextField idactualiza;
    private JTextField canchactual;
    private JTextField ubicaactual;
    private JButton actualizarButton;

    /**
     * Constructor que inicializa el formulario de administración de canchas y configura los componentes de la interfaz.
     */
    public canchaAdmin() {
        setTitle("Canchas");
        setSize(550, 425);
        setContentPane(canchaadmin);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idcancha.getText();
                String cancha = canchanombre.getText();
                String ubicacion = ubicancha.getText();
                crearCancha(id, cancha, ubicacion);
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

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idactual = idactualiza.getText();
                String nomactual = canchactual.getText();
                String ubiactua = ubicaactual.getText();

                if (idactual.trim().isEmpty() || nomactual.trim().isEmpty() || ubiactua.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Connection connection = ConexionBase.getConnection();
                if (connection != null) {
                    try {
                        String query = "UPDATE canchas SET nombre = ?, ubicacion = ? WHERE id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nomactual);
                        preparedStatement.setString(2, ubiactua);
                        preparedStatement.setString(3, idactual);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Cancha actualizada con éxito");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ninguna cancha con la ID proporcionada");
                        }

                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clienteBuscado = (String) boxeliminar.getSelectedItem();

                if (clienteBuscado == null || clienteBuscado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una cancha para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la opción seleccionada?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    String[] partes = clienteBuscado.split(" - ");
                    int IDcancha = Integer.parseInt(partes[0]);

                    Connection connection = ConexionBase.getConnection();
                    if (connection != null) {
                        try {
                            String deleteQuery = "DELETE FROM canchas WHERE ID = ?";
                            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                            deleteStatement.setInt(1, IDcancha);
                            int rowsAffected = deleteStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Cancha eliminada con éxito");
                                cargarCancha();
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo eliminar la cancha", "Error", JOptionPane.ERROR_MESSAGE);
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

    /**
     * Crea una nueva cancha en la base de datos.
     *
     * @param id        El identificador de la cancha.
     * @param cancha    El nombre de la cancha.
     * @param ubicacion La ubicación de la cancha.
     */
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
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga y muestra la cancha correspondiente al ID proporcionado en el JComboBox.
     */
    private void cargarCancha() {
        String idelimina = ideliminar.getText();
        if (idelimina.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo ID es obligatorio para cargar la cancha", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection connection = ConexionBase.getConnection();
        if (connection != null) {
            try {
                boxeliminar.removeAllItems();

                String query = "SELECT id, nombre, ubicacion FROM canchas WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, idelimina);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int idcanc = resultSet.getInt("id");
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
