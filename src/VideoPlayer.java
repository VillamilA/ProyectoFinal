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

public class VideoPlayer extends JFrame {

    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;
    private List<String> videoPaths;
    private int currentVideoIndex = 0;

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

    private void loadAndPlayVideo(int index) {
        if (index < 0 || index >= videoPaths.size()) {
            JOptionPane.showMessageDialog(this, "No hay más videos para reproducir.");
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

    private void loadNextVideo() {
        if (currentVideoIndex + 1 < videoPaths.size()) {
            currentVideoIndex++;
            loadAndPlayVideo(currentVideoIndex);
        } else {
            JOptionPane.showMessageDialog(this, "No hay más videos para reproducir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VideoPlayer().setVisible(true));
    }
}
