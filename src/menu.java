import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa el menú principal de la aplicación, ofreciendo varias opciones de gestión de reservas.
 */
public class menu extends JFrame {
    private JButton reservarButton;
    private JButton consultarReservaButton;
    private JButton eliminarReservaButton;
    private JButton actualizarReservaButton;
    private JPanel menuu;
    private JButton regresarButton;
    private JButton verVideoButton;

    /**
     * Constructor que inicializa la interfaz del menú principal con varias opciones.
     */
    public menu() {
        setTitle("Menú");
        setSize(550, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(menuu);
        setLocationRelativeTo(null);

        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana para reservar una cancha
                new reservarForm().setVisible(true);
                dispose();
            }
        });

        consultarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana para consultar reservas
                new consultaForm().setVisible(true);
                dispose();
            }
        });

        eliminarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana para eliminar una reserva
                new eliminarForm().setVisible(true);
                dispose();
            }
        });

        actualizarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana para actualizar una reserva
                new actualizarForm().setVisible(true);
                dispose();
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Regresa a la ventana de inicio de sesión
                new login().setVisible(true);
                dispose();
            }
        });

        verVideoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana para ver un video
                new VideoPlayer().setVisible(true);
            }
        });
    }
}
