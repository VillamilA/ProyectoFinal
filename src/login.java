import com.sun.source.tree.IfTree;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame{
    private JTextField campoUsu;
    private JPasswordField campoContra;
    private JButton ingresarButton;
    private JButton registrarmeButton;
    private JPanel logeo;

    public login(){
    setTitle("Iniciar Sesión");
    setSize(500,600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(logeo);
    setLocationRelativeTo(null);


        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsu.getText();
                String contrasena = new String(campoContra.getPassword());

                if (usuario.trim().isEmpty() || contrasena.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Por favor ingrese los datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (autenticarUsuario(usuario, contrasena)){
                    new menu().setVisible(true);
                dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        });
        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             new registrarForm().setVisible(true);
            }
        });
    }

private boolean autenticarUsuario(String usuario, String contrasena){
        boolean autenticado = false;
    Connection connection = ConexionBase.getConnection();
    String query = "SELECT * from usuario where username =? and password =?";

    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, usuario);
        preparedStatement.setString(2,contrasena);

        ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()){
    autenticado = true;
        }
    }

    catch (SQLException e) {
        e.printStackTrace();
        }
return autenticado;
    }

    public static void main(String[] args){
        new login().setVisible(true);
    }
}


