package com.atri.view;

import com.atri.scene.GameOverScene;
import com.atri.scene.GameScene;
import com.atri.scene.IndexScene;
import com.atri.scene.RecentRecordScene;
import com.atri.service.RecentRecordService;
import com.atri.util.RoleAndSpeed;
import jakarta.annotation.Resource;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Director 类（单例模式）
 * <p>
 *     控制页面的跳转、Canvas 的绘制以及界面初始化等功能。
 *     通过初始化场景 {@code Scene} 和窗口 {@code Stage}，来创建和显示界面。
 * </p>
 */
@Component
public class Director {

    // 默认窗口宽度和高度
    public static final double DEFAULT_WIDTH = 800, DEFAULT_HEIGHT = 600;

    public static final double GRID_SIZE = 20;
    public static final double GAME_WIDTH = 600;
    public static final double GAME_HEIGHT = 400;

    @Getter
    public static Director instance = new Director();

    private Stage stage;

    @Getter
    @Setter
    public static RoleAndSpeed roleAndSpeed;

    @Resource
    private RecentRecordService recentRecordService;

    private final GameScene gameScene = new GameScene();

    private final GameOverScene gameOverScene = new GameOverScene();

    /**
     * 初始化应用界面
     * <p>
     *     该方法创建一个包含 Canvas 的界面，并设置窗口大小和标题，
     * </p>
     * @param stage JavaFX 的窗口对象
     */
    public void init(Stage stage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        canvas.setWidth(root.getWidth());
        canvas.setHeight(root.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("PixelPython");
        stage.getIcons().add(new Image(Objects.
                requireNonNull(getClass().getResource("/image/icon.png")).toExternalForm()));
//        stage.setResizable(false);

        this.stage = stage;
        toIndex();
        stage.show();
    }

    /**
     * 转到主页
     */
    public void toIndex() {
        IndexScene.load(stage);
    }

    /**
     * 转到历史记录页面
     */
    public void toRecentRecord() {
        RecentRecordScene.load(stage);
    }

    /**
     * 转到游戏界面
     */
    public void gameStart() {
        gameScene.initialize(stage);
    }

    /**
     * 转到游戏结束界面
     */
    public void gameOver() {
        gameOverScene.showGameOverPopup(this.stage);
    }
}
