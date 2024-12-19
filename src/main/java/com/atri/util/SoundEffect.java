package com.atri.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public enum SoundEffect {
    BUTTON_CLICK("/sound/button_click.mp3"),
    EAT("/sound/eat.mp3");

    private final String filePath;

    private MediaPlayer mediaPlayer;

    SoundEffect(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media sound = new Media(Objects.requireNonNull(
                SoundEffect.class.getResource(filePath)).toExternalForm());

        mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.dispose());
        mediaPlayer.play();
    }
}
