package com.atri.scene;

import com.atri.sprite.Background;
import com.atri.sprite.Food;
import com.atri.sprite.Python;
import com.atri.util.Direction;
import com.atri.view.Director;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.lang.invoke.VarHandle;

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

    private final Python python = new Python(0, 0, Direction.RIGHT, 0);
    private Timeline pythonMoveTimeLine;

    private Stage stage;

    private Label scoreLabel; // 显示得分的标签
    private int score = 0; // 初始得分为 0


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
        gameGc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        background.paint(backgroundGc);

        drawGrid(gameGc);

        if (!food.isAlive()) {
            food.snack(python.getBody());
            food.drawFood(gameGc);
            food.setAlive(true);
        }
        if (food.isAlive()) {
            food.drawFood(gameGc);
        }

        checkFoodCollision();

        python.draw(gameGc);
        if (running) {
            if (pythonMoveTimeLine.getStatus() == Animation.Status.STOPPED) {
                pythonMoveTimeLine.play();
            }
        } else {
            pythonMoveTimeLine.stop();
        }
    }

    public void initialize(Stage stage) {
        this.stage = stage;
        pythonMoveTimeLine = new Timeline(
                new KeyFrame(Duration.millis(Director.getSpeed().getSpeed()), e -> {
                    if (running) python.move();

                }));
        pythonMoveTimeLine.setCycleCount(Timeline.INDEFINITE);

        food.snack(python.getBody());
        running = true;

        AnchorPane root = new AnchorPane();

        // 设置边框（使用一个外部容器来给 Canvas 加上边框）
        Rectangle border = new Rectangle(100, 50, GAME_WIDTH, GAME_HEIGHT);
        border.setFill(null);           // 边框背景透明
        border.setStroke(Color.BLACK);  // 边框颜色
        border.setStrokeWidth(4);       // 边框宽度

        gameCanvas.setLayoutX(100);
        gameCanvas.setLayoutY(50);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 20));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setLeftAnchor(scoreLabel, 10.0); // 固定在左下角
        AnchorPane.setBottomAnchor(scoreLabel, 10.0);

        root.getChildren().addAll(backgroundCanvas, border, gameCanvas, scoreLabel);
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
            switch (code) {
                case SPACE -> running = !running;
                case UP -> python.setDirection(Direction.UP);
                case DOWN -> python.setDirection(Direction.DOWN);
                case LEFT -> python.setDirection(Direction.LEFT);
                case RIGHT -> python.setDirection(Direction.RIGHT);
            }
        }
    }

    public void toggleGameState() {
        running = !running;
    }

    public void checkFoodCollision() {
        Python.Segment head = python.getHead();

        if (head.x == food.getX() && head.y == food.getY() - 1) {
            python.grow();
            food.setAlive(false);
            score += 100;
            scoreLabel.setText("Score: " + score);
        }

        if (head.x < 0 || head.x >= (GAME_WIDTH / GRID_SIZE) ||
                head.y < 0 || head.y >= (GAME_HEIGHT / GRID_SIZE) )
            endGame("游戏结束");

    }

    public void endGame(String message) {
        running = false;
        pythonMoveTimeLine.stop();
        System.out.println(message);
        Python.Segment head = python.getHead();
        Python.Segment last = python.getOldBody().getLast();
        int headX = head.x;
        int headY = head.y;
        int lastX = last.x;
        int lastY = last.y;

        if (headX < 0) {
            python.addTailUpdateHead(lastX, lastY);    // 左越界补全
        } else if (headX >= (GAME_WIDTH / GRID_SIZE)) {
            python.addTailUpdateHead(lastX, lastY);    // 右越界补全
        } else if (headY < 0) {
            python.addTailUpdateHead(lastX, lastY);    // 上越界补全
        } else if (headY >= (GAME_HEIGHT / GRID_SIZE)) {
            python.addTailUpdateHead(headX, lastY);    // 下越界补全
        }

        paint();

        showGameOverPopup(this.stage);
    }

    public void showGameOverPopup(Stage stage) {
        // 暂停游戏状态
        running = false;
        pythonMoveTimeLine.stop();

        // 创建新的模态窗口
        Stage popupStage = new Stage();
        // 设置为模态窗口
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.TRANSPARENT); // 设置透明背景
        popupStage.initOwner(stage); // 设置所有者为主窗口

        // 弹窗背景（透明）
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");

        // “GAME OVER”文本
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 50));
        gameOverText.setFill(Color.GREEN);

        // 重新开始按钮
        Button restartButton = new Button("重新开始");
        restartButton.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 30));
        restartButton.setTextFill(Color.WHITE);
        restartButton.setStyle("""
            -fx-background-color: transparent;
            -fx-border-color: white;
            -fx-border-width: 2;
            -fx-border-radius: 5;
            -fx-padding: 5 20 5 20;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 10, 0, 0, 0);
            """);
        restartButton.setOnAction(e -> {
            popupStage.close();
            restartGame(stage); // 调用重新开始逻辑
        });

        // 返回主页按钮
        Button exitButton = new Button("返回主页");
        exitButton.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 30));
        exitButton.setTextFill(Color.WHITE);
        exitButton.setStyle(restartButton.getStyle());
        exitButton.setOnAction(e -> {
            popupStage.close();
            Director.getInstance().toIndex();
        });

        // 按钮布局
        VBox buttonLayout = new VBox(10, restartButton, exitButton);
        buttonLayout.setStyle("-fx-alignment: center;");

        // 主布局
        VBox layout = new VBox(20, gameOverText, buttonLayout);
        layout.setStyle("-fx-alignment: center;");

        root.getChildren().add(layout);

        // 创建场景
        Scene scene = new Scene(root, 300, 200);
        scene.setFill(Color.TRANSPARENT);

        popupStage.setScene(scene);
        popupStage.centerOnScreen(); // 居中显示
        popupStage.show();
    }

    private void restartGame(Stage stage) {
        python.reset(); // 重置蛇状态（需要在 Python 类中实现 reset 方法）
        food.setAlive(false); // 重置食物状态
        score = 0;
        running = true;
        pythonMoveTimeLine.play(); // 重新开始动画

        paint(); // 重绘场景
    }

}
