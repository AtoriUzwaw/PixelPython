package com.atri.view;

import com.atri.scene.GameScene;
import com.atri.scene.IndexScene;
import com.atri.scene.RecentRecordScene;
import com.atri.service.RecentService;
import com.atri.util.Speed;
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

    private Stage stage;

    @Getter
    @Setter
    public static Speed speed;

    @Getter
    private static Director instance = new Director();

    private GameScene gameScene = new GameScene();

    @Resource
    RecentService recentService;




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

    public void toIndex() {
        IndexScene.load(stage);
    }

    public void toRecentRecord() {
        RecentRecordScene.load(stage);
    }

    public void gameStart() {
        gameScene.initialize(stage);
    }

    public void gameOver() {}
}
