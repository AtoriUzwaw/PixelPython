package com.atri.scene;

import com.atri.sprite.Background;
import com.atri.sprite.Food;
import com.atri.view.Director;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameScene {
//    720, 480
    private final double GAME_WIDTH = 600;
    private final double GAME_HEIGHT = 400;
    private final int GRID_SIZE = 20;

    private final Canvas backgroundCanvas = new Canvas(Director.DEFAULT_WIDTH, Director.DEFAULT_HEIGHT);
    private final Canvas gameCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    private final GraphicsContext backgroundGc = backgroundCanvas.getGraphicsContext2D();
    private final GraphicsContext gameGc = gameCanvas.getGraphicsContext2D();

    private final KeyProcess keyProcess = new KeyProcess();
    private final Refresh refresh = new Refresh();
    private boolean running = false;

    private final Background background = new Background();
    private final Food food = new Food(true);

    public void drawGrid(GraphicsContext gc) {

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        for (int x = 0; x < GAME_WIDTH; x += GRID_SIZE) {
            gc.strokeLine(x, 0, x, GAME_HEIGHT);
        }

        for (int y = 0; y < GAME_HEIGHT; y += GRID_SIZE) {
            gc.strokeLine(0, y, GAME_WIDTH, y);
        }
    }

    public void paint() {
        background.paint(backgroundGc);

        drawGrid(gameGc);

        if (!food.isAlive()) {
            food.snack(0, 0);
            food.drawFood(gameGc);
            food.setAlive(true);
        }
        if (food.isAlive()) {
            food.drawFood(gameGc);
        }
    }

    public void initialize(Stage stage) {
        food.setAlive(false);
        running = true;

        AnchorPane root = new AnchorPane();

        // 设置边框（使用一个外部容器来给 Canvas 加上边框）
        Rectangle border = new Rectangle(100, 50, GAME_WIDTH, GAME_HEIGHT);
        border.setFill(null);           // 边框背景透明
        border.setStroke(Color.BLACK);  // 边框颜色
        border.setStrokeWidth(4);       // 边框宽度

        gameCanvas.setLayoutX(100);
        gameCanvas.setLayoutY(50);

        root.getChildren().addAll(backgroundCanvas, border, gameCanvas);
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
