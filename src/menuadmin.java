import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuadmin extends JFrame {
    private JButton clientesButton;
    private JButton trabajadoresButton;
    private JButton canchaButton;
    private JButton salirButton;
    private JPanel menuadmin;

    public menuadmin()
    {
        setTitle("Administrador");
        setSize(500,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(menuadmin);
        setLocationRelativeTo(null);

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        canchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new canchaAdmin().setVisible(true);
                dispose();
            }
        });
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new clienteAdmin().setVisible(true);
                dispose();
            }
        });
        trabajadoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new trabajadorAdmin().setVisible(true);
                dispose();
            }
        });
    }
}
