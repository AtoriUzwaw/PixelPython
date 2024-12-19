package com.atri.scene;

import com.atri.sprite.Background;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameScene {
//    720, 480
    private final double GAME_WIDTH = 800;
    private final double GAME_HEIGHT = 600;

    private final Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();
    private Refresh refresh = new Refresh();
    private boolean running = false;

    private Background background = new Background();

    public void paint() {
        background.paint(gc);
    }

    public void initialize(Stage stage) {
        running = true;
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        stage.getScene().setOnKeyPressed(keyProcess);
        refresh.start();
    }

    public void clear(Stage stage) {
        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        refresh.stop();
    }

    private class Refresh extends AnimationTimer {

        @Override
        public void handle(long l) {
            if (running) paint();
        }
    }

    private class KeyProcess implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            KeyCode code = event.getCode();
            if (KeyCode.SPACE.equals(code)) running = !running;
        }
    }

    public void toggleGameState() {
        running = !running;
    }
}
