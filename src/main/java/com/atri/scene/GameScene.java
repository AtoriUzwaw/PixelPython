package com.atri.scene;

import com.atri.config.AppConfig;
import com.atri.service.RecentRecordService;
import com.atri.service.impl.RecentRecordServiceImpl;
import com.atri.sprite.Background;
import com.atri.sprite.Food;
import com.atri.sprite.Python;
import com.atri.util.Direction;
import com.atri.util.SoundEffect;
import com.atri.util.TranslationUtil;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;

@Controller
public class GameScene {
    //    720, 480
    private final double GAME_WIDTH = 600;
    private final double GAME_HEIGHT = 400;
    private final double GRID_SIZE = Director.GRID_SIZE;

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

    private RecentRecordService recentRecordService;

    private String ROLE;



    public void drawGrid(GraphicsContext gc) {

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        for (double x = 0; x < GAME_WIDTH; x += GRID_SIZE) {
            gc.strokeLine(x, 0, x, GAME_HEIGHT);
        }

        for (double y = 0; y < GAME_HEIGHT; y += GRID_SIZE) {
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
            food.move();
            food.drawFood(gameGc);
        }

        checkCollision();


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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        recentRecordService = context.getBean(RecentRecordServiceImpl.class);

        this.ROLE = TranslationUtil.translation(Director.getRoleAndSpeed(), true);

        this.stage = stage;

        this.python.reset();

        pythonMoveTimeLine = new Timeline(
                new KeyFrame(Duration.millis(Director.getRoleAndSpeed().getSpeed()), e -> {
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

        // SCORE 标签
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setLeftAnchor(scoreLabel, 90.0);   // 向右移动
        AnchorPane.setBottomAnchor(scoreLabel, 80.0); // 向上移动

        // ROLE 标签
        Label roleLabel = new Label("Role: " + ROLE);
        roleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42)); // 字体可以调整
        roleLabel.setTextFill(Color.WHITE);
        roleLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setRightAnchor(roleLabel, 90.0); // 设置 roleLabel 距离左边的距离，可以调整
        AnchorPane.setBottomAnchor(roleLabel, 80.0); // 保持垂直位置一致

        // control 标签
        Label controlLabel = new Label("操作方式: 使用上、下、左、右进行移动\n\t使用 SPACE 暂停");
        controlLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 28));
        controlLabel.setTextFill(Color.WHITE);
        controlLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setLeftAnchor(controlLabel, 240.0);
        AnchorPane.setBottomAnchor(controlLabel, 10.0);

        root.getChildren().addAll(backgroundCanvas, border, gameCanvas, scoreLabel, roleLabel, controlLabel);
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
                case SPACE -> {
                    SoundEffect.KEYCODE_PRESS.play();
                    running = !running;
                }
                // 方向键的音效在 setDirection() 方法中
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

    public void checkCollision() {
        if (!running) return;
        LinkedList<Python.Segment> body = python.getBody();
        Python.Segment head = body.get(0);

        if (head.x == food.getX() && head.y == food.getY() - 1) {
            SoundEffect.EAT.play();
            python.grow();
            food.setAlive(false);
            score += 100;
            scoreLabel.setText("Score: " + score);
        }

        if (head.x < 0 || head.x >= (GAME_WIDTH / GRID_SIZE) ||
                head.y < 0 || head.y >= (GAME_HEIGHT / GRID_SIZE))
            endGame("wall");

        if (python.checkSelfCollision()) endGame("self");
    }

    public void endGame(String message) {
        if (!running) return;
        running = false;
        pythonMoveTimeLine.stop();
        System.out.println(message);

        python.addTailUpdateHead(); // 转移头部，补全尾部



        showGameOverPopup(this.stage);
        String role = TranslationUtil.translation(Director.getRoleAndSpeed());
        recentRecordService.addRecentRecord(role, score);
    }

    public void showGameOverPopup(Stage stage) {

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
            SoundEffect.BUTTON_CLICK.play();
            popupStage.close();
            restartGame(); // 调用重新开始逻辑
        });

        // 返回主页按钮
        Button exitButton = new Button("返回主页");
        exitButton.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 30));
        exitButton.setTextFill(Color.WHITE);
        exitButton.setStyle(restartButton.getStyle());
        exitButton.setOnAction(e -> {
            SoundEffect.BUTTON_CLICK.play();
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

    private void restartGame() {
        python.reset();
        score = 0;
        food.setAlive(false);
        running = true;
        pythonMoveTimeLine.play();
    }

}
