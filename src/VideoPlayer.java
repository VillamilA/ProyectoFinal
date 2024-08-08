import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Video proporciona una interfaz gráfica de usuario
 * para reproducir videos utilizando JavaFX integrado en Swing. Esta clase permite la reproducción
 * de una lista de videos y ofrece un botón para avanzar al siguiente video en la lista.
 * @author AVillamil
 * @version 1.0
 */
public class VideoPlayer extends JFrame {

    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;
    private List<String> videoPaths;
    private int currentVideoIndex = 0;

    /**
     * Crea una nueva instancia de {@code VideoPlayer}.
     * Configura el título, tamaño, y la operación de cierre de la ventana.
     * Inicializa el panel de JavaFX, el panel de botones y la lista de rutas de videos.
     * Carga y reproduce el video inicial.
     */
    public VideoPlayer() {
        setTitle("Reproduciendo Video");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configurar el JFXPanel para integrar JavaFX en Swing
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        JButton siguienteButton = new JButton("Ver Siguiente Cancha");
        buttonPanel.add(siguienteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listado de videos
        videoPaths = new ArrayList<>();
        videoPaths.add("src/imagenFondo/futbol.mp4");
        videoPaths.add("src/imagenFondo/otro_video.mp4");
        // Agrega más rutas de videos según sea necesario

        // Cargar y reproducir el video inicial
        loadAndPlayVideo(currentVideoIndex);

        // Manejar la acción del botón
        siguienteButton.addActionListener(e -> loadNextVideo());
    }

    /**
     * Carga y reproduce el video en la posición especificada de la lista de videos.
     * Si el índice está fuera del rango de la lista, muestra un mensaje de advertencia.
     *
     * @param index El índice del video en la lista {@code videoPaths} que se va a reproducir.
     */
    private void loadAndPlayVideo(int index) {
        if (index < 0 || index >= videoPaths.size()) {
            JOptionPane.showMessageDialog(this, "No hay mas partidos en vivo que mostrar.");
            return;
        }

        Platform.runLater(() -> {
            // Ruta del video
            Media media = new Media(Paths.get(videoPaths.get(index)).toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() -> {
                // Cerrar la ventana al finalizar el video
                SwingUtilities.invokeLater(this::dispose);
            });
            MediaView mediaView = new MediaView(mediaPlayer);

            Scene scene = new Scene(new javafx.scene.Group(mediaView), 1080, 720);
            fxPanel.setScene(scene);
            mediaPlayer.play();
        });
    }

    /**
     * Avanza al siguiente video en la lista {@code videoPaths}.
     * Si no hay más videos en la lista, muestra un mensaje de advertencia.
     */
    private void loadNextVideo() {
        if (currentVideoIndex + 1 < videoPaths.size()) {
            currentVideoIndex++;
            loadAndPlayVideo(currentVideoIndex);
        } else {
            JOptionPane.showMessageDialog(this, "No hay mas partidos en vivo que mostrar.");
        }
    }

    /**
     * El punto de entrada principal para ejecutar la aplicación.
     * Crea y muestra una instancia de {@code VideoPlayer}.
     *
     * @param args Los argumentos de línea de comandos (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VideoPlayer().setVisible(true));
    }
}
