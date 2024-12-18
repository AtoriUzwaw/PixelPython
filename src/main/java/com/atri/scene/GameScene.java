package com.atri.scene;

import com.atri.service.KeyProcessService;
import com.atri.service.RefreshService;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameScene {
    private final double GAME_WIDTH = 720;
    private final double GAME_HEIGHT = 480;

    private Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    private final KeyProcessService keyProcessService;
    private final RefreshService refreshService;

    @Autowired
    public GameScene(KeyProcessService keyProcessService, RefreshService refreshService) {
        this.keyProcessService = keyProcessService;
        this.refreshService = refreshService;
    }

    private void paint() {

    }

    public void initialize(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.setScene(root.getScene());
        stage.getScene().setOnKeyPressed(keyProcessService);        // 设置键盘事件监听器
        if (keyProcessService.isRunning()) refreshService.start();  // 如果游戏正在运行，开始刷新
    }

    public void clear(Stage stage) {
        // 清除键盘事件监听器
        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyProcessService);
    }

    public void pauseOrContinue() {
        keyProcessService.toggleGameState();    // 切换游戏状态
    }
}
