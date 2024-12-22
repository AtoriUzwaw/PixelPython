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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.LinkedList;

/**
 * 游戏界面类，负责初始化和渲染游戏场景、处理用户输入以及游戏逻辑。
 * 包含背景绘制、蛇的移动、食物生成和碰撞检测等功能。
 */
public class GameScene {
    private final double GAME_WIDTH = Director.GAME_WIDTH;    // 游戏界面宽度
    private final double GAME_HEIGHT = Director.GAME_HEIGHT;  // 游戏界面高度
    private final double GRID_SIZE = Director.GRID_SIZE;      // 网格大小

    private final Canvas backgroundCanvas = new Canvas(Director.DEFAULT_WIDTH, Director.DEFAULT_HEIGHT);  // 背景画布
    private final Canvas gameCanvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);  // 游戏画布
    private final GraphicsContext backgroundGc = backgroundCanvas.getGraphicsContext2D();  // 背景绘图上下文
    private final GraphicsContext gameGc = gameCanvas.getGraphicsContext2D();  // 游戏绘图上下文

    private final KeyProcess keyProcess = new KeyProcess();     // 键盘输入处理
    private final Refresh refresh = new Refresh();              // 自动刷新游戏画面

    private final Background background = new Background();     // 背景对象
    private final Food food = new Food(true);              // 食物对象
    private final Python python = new Python(Direction.RIGHT);  // 初始化蛇的方向为右
    private Timeline pythonMoveTimeLine;                        // 蛇的移动定时器

    private boolean running = false;  // 游戏状态，是否在运行
    private Label scoreLabel;         // 分数标签
    private int score = 0;            // 初始得分

    private RecentRecordService recentRecordService;  // 最近成绩服务

    /**
     * 初始化游戏场景，设置界面、控制和数据服务。
     *
     * @param stage JavaFX 的 Stage 对象，用于显示游戏界面
     */
    public void initialize(Stage stage) {
        // 初始化数据库服务
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        recentRecordService = context.getBean(RecentRecordServiceImpl.class);

        // 获取用户角色
        String ROLE = TranslationUtil.translation(Director.getRoleAndSpeed(), true);

        this.python.reset();            // 初始化蛇的状态
        food.snack(python.getBody());   // 初始化食物的位置
        running = true;                 // 设置游戏为运行状态

        // 动态控制蛇的移动速度
        pythonMoveTimeLine = new Timeline(
                new KeyFrame(Duration.millis(Director.getRoleAndSpeed().getSpeed()), e -> {
                    if (running) python.move();                 // 每个时间间隔移动一次蛇
                }));
        pythonMoveTimeLine.setCycleCount(Timeline.INDEFINITE);  // 无限循环

        // 设置前端UI界面
        AnchorPane root = new AnchorPane();
        Rectangle border = new Rectangle(100, 50, GAME_WIDTH, GAME_HEIGHT);  // 设置游戏边框
        border.setFill(null);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(4);

        gameCanvas.setLayoutX(100);
        gameCanvas.setLayoutY(50);

        // 创建得分标签
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setLeftAnchor(scoreLabel, 90.0);
        AnchorPane.setBottomAnchor(scoreLabel, 80.0);

        // 创建角色标签
        Label roleLabel = new Label("Role: " + ROLE);
        roleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42));
        roleLabel.setTextFill(Color.WHITE);
        roleLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setRightAnchor(roleLabel, 90.0);
        AnchorPane.setBottomAnchor(roleLabel, 80.0);

        // 控制提示标签
        Label controlLabel = new Label("""
                操作方式: 使用上、下、左、右进行移动
                \t使用 SPACE 暂停
                暂停状态下按 ESC 返回主页面""");
        controlLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 28));
        controlLabel.setTextFill(Color.WHITE);
        controlLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 5;");
        AnchorPane.setLeftAnchor(controlLabel, 240.0);
        AnchorPane.setBottomAnchor(controlLabel, 10.0);

        root.getChildren().addAll(backgroundCanvas, border, gameCanvas, scoreLabel, roleLabel, controlLabel);
        stage.getScene().setRoot(root);
        stage.getScene().setOnKeyPressed(keyProcess);  // 绑定键盘事件

        refresh.start();  // 启动刷新
    }

    /**
     * 绘制背景网格，用于显示游戏区域的网格线。
     *
     * @param gc GraphicsContext 对象，用于绘制网格
     */
    public void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // 绘制纵向网格
        for (double x = 0; x < GAME_WIDTH; x += GRID_SIZE) {
            gc.strokeLine(x, 0, x, GAME_HEIGHT);
        }

        // 绘制横向网格
        for (double y = 0; y < GAME_HEIGHT; y += GRID_SIZE) {
            gc.strokeLine(0, y, GAME_WIDTH, y);
        }
    }

    /**
     * 游戏渲染方法，负责更新游戏画面。
     * 绘制背景、蛇、食物，并进行碰撞检测。
     */
    public void paint() {
        gameGc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);  // 清空画布
        background.paint(backgroundGc);    // 绘制背景
        drawGrid(gameGc);  // 绘制网格

        if (!food.isAlive()) {
            food.snack(python.getBody());  // 食物被吃掉后重新生成
            food.drawFood(gameGc);
            food.setAlive(true);
        }
        if (food.isAlive()) {
            food.move();
            food.drawFood(gameGc);
        }

        checkCollision();     // 检查碰撞

        python.draw(gameGc);  // 绘制蛇

        if (running) {
            if (pythonMoveTimeLine.getStatus() == Animation.Status.STOPPED) {
                pythonMoveTimeLine.play();  // 游戏继续
            }
        } else {
            pythonMoveTimeLine.stop();      // 停止游戏
        }
    }

    /**
     * 处理键盘输入事件，用于控制游戏中的蛇。
     */
    private class KeyProcess implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            KeyCode code = event.getCode();
            switch (code) {
                case SPACE -> {
                    SoundEffect.KEYCODE_PRESS.play();
                    running = !running;                        // 切换游戏暂停状态
                }
                case ESCAPE -> {
                    if (!running) {                            // 暂停游戏期间按下 ESC 退出游戏
                        SoundEffect.KEYCODE_PRESS.play();
                        Director.getInstance().toIndex();      // 返回主菜单
                    }
                }
                case UP -> python.setDirection(Direction.UP);  // 控制蛇的方向
                case DOWN -> python.setDirection(Direction.DOWN);
                case LEFT -> python.setDirection(Direction.LEFT);
                case RIGHT -> python.setDirection(Direction.RIGHT);
            }
        }
    }

    /**
     * 碰撞检测方法，检查蛇是否碰到墙壁、自己或者吃到食物。
     */
    public void checkCollision() {
        if (!running) return;
        LinkedList<Python.Segment> body = python.getBody();
        Python.Segment head = body.get(0);

        // 检查是否吃到食物
        if (head.x == food.getX() && head.y == food.getY() - 1) {
            SoundEffect.EAT.play();
            python.grow();                          // 增加蛇身
            food.setAlive(false);                   // 食物消失
            score += 100;                           // 更新分数
            scoreLabel.setText("Score: " + score);  // 更新分数标签
        }

        // 检查蛇是否撞墙
        if (head.x < 0 || head.x >= (GAME_WIDTH / GRID_SIZE) ||
                head.y < 0 || head.y >= (GAME_HEIGHT / GRID_SIZE))
            endGame("wall");  // 碰到墙壁结束游戏

        // 检查蛇是否自撞
        if (python.checkSelfCollision()) endGame("self");  // 自己撞到自己结束游戏
    }

    /**
     * 结束游戏的操作，包括播放游戏结束音效、记录成绩等。
     *
     * @param message 游戏结束时输出的消息
     */
    public void endGame(String message) {
        if (!running) return;
        SoundEffect.GAME_OVER.play();       // 播放游戏结束音效
        running = false;
        pythonMoveTimeLine.stop();          // 停止蛇的移动
        System.out.println(message);

        python.addTailUpdateHead();         // 补全蛇身
        Director.getInstance().gameOver();  // 显示游戏结束弹窗
        String role = TranslationUtil.translation(Director.getRoleAndSpeed());
        recentRecordService.addRecentRecord(role, score);  // 保存成绩到数据库
    }

    /**
     * 自动刷新游戏画面，用于定时更新画面。
     */
    private class Refresh extends AnimationTimer {
        @Override
        public void handle(long l) {
            if (running) paint();  // 如果游戏在运行，刷新画面
        }
    }
}

