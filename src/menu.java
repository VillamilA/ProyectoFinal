import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu extends JFrame {
    private JButton reservarButton;
    private JButton consultarReservaButton;
    private JButton eliminarReservaButton;
    private JButton actualizarReservaButton;
    private JPanel menuu;

    public menu(){
    setTitle("Menú");
    setSize(550,400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(menuu);
    setLocationRelativeTo(null);
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new reservarForm().setVisible(true);
                dispose();
            }
        });
        consultarReservaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new consultaForm().setVisible(true);
                dispose();
            }
        });
        eliminarReservaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarForm().setVisible(true);
                dispose();
            }
        });
        actualizarReservaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new actualizarForm().setVisible(true);
                dispose();
            }
        });
    }


}
