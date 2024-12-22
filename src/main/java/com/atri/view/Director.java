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
 * Director 类负责管理游戏的各个场景和界面跳转。
 * 它作为一个单例类，管理应用程序的主窗口、游戏场景、游戏结束场景和历史记录页面的跳转。
 * 该类还负责初始化应用程序界面，并通过 JavaFX 控件显示各个场景。
 */
@Component
public class Director {

    // 默认窗口宽度和高度
    public static final double DEFAULT_WIDTH = 800, DEFAULT_HEIGHT = 600;

    // 网格大小
    public static final double GRID_SIZE = 20;

    // 游戏区域的宽度和高度
    public static final double GAME_WIDTH = 600;
    public static final double GAME_HEIGHT = 400;

    // 单例模式，获取唯一的 Director 实例
    @Getter
    public static Director instance = new Director();

    // 游戏窗口
    private Stage stage;

    // 角色与速度设置
    @Getter
    @Setter
    public static RoleAndSpeed roleAndSpeed;

    // 游戏场景实例
    private final GameScene gameScene = new GameScene();

    // 游戏结束场景实例
    private final GameOverScene gameOverScene = new GameOverScene();

    /**
     * 初始化应用界面
     * <p>
     *     该方法创建一个包含 Canvas 的界面，并设置窗口大小和标题，初始化 JavaFX 的主要场景。
     * </p>
     * @param stage JavaFX 的窗口对象，用于显示应用界面。
     */
    public void init(Stage stage) {
        // 创建根容器 BorderPane 和画布 Canvas
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        canvas.setWidth(root.getWidth());
        canvas.setHeight(root.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 创建场景并将其设置到舞台
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("PixelPython");

        // 设置窗口图标
        stage.getIcons().add(new Image(Objects.
                requireNonNull(getClass().getResource("/image/icon.png")).toExternalForm()));

        // 设置舞台并跳转到主页
        this.stage = stage;
        toIndex();
        stage.show();
    }

    /**
     * 跳转到主页场景
     */
    public void toIndex() {
        IndexScene.load(stage); // 加载主页场景
    }

    /**
     * 跳转到历史记录页面
     */
    public void toRecentRecord() {
        RecentRecordScene.load(stage); // 加载历史记录场景
    }

    /**
     * 启动游戏并显示游戏界面
     */
    public void gameStart() {
        gameScene.initialize(stage); // 初始化游戏场景
    }

    /**
     * 显示游戏结束界面
     */
    public void gameOver() {
        gameOverScene.showGameOverPopup(this.stage); // 显示游戏结束弹窗
    }
}
