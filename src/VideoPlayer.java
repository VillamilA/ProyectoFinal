import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class VideoPlayer extends JFrame {
    public VideoPlayer() {
        setTitle("Reproduciendo Video");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configurar JFXPanel para integrar JavaFX en Swing
        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            // Ruta del video
            Media media = new Media(Paths.get("src/imagenFondo/futbol.mp4").toUri().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() -> {
                // Cerrar la ventana al finalizar el video
                SwingUtilities.invokeLater(this::dispose);
            });
            MediaView mediaView = new MediaView(mediaPlayer);

            Scene scene = new Scene(new javafx.scene.Group(mediaView), 800, 600);
            fxPanel.setScene(scene);
            mediaPlayer.play();
        });
    }
}
