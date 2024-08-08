import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa el menú principal para el administrador de la aplicación.
 * Permite al administrador acceder a diferentes funcionalidades como la gestión de clientes,
 * trabajadores y canchas, así como salir de la aplicación.
 * @author AVillamil
 * @version 1.0
 */
public class menuadmin extends JFrame {
    private JButton clientesButton;
    private JButton trabajadoresButton;
    private JButton canchaButton;
    private JButton salirButton;
    private JPanel menuadmin;

    /**
     * Constructor que inicializa el menú del administrador y configura los componentes de la interfaz.
     */
    public menuadmin() {
        setTitle("Administrador");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(menuadmin);
        setLocationRelativeTo(null);

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra la aplicación
                System.exit(0);
            }
        });

        canchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre el formulario para la gestión de canchas
                new canchaAdmin().setVisible(true);
                dispose();
            }
        });

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre el formulario para la gestión de clientes
                new clienteAdmin().setVisible(true);
                dispose();
            }
        });
    }
}
