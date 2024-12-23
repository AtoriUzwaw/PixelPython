package com.atri.scene;

import com.atri.util.SoundEffect;
import com.atri.view.Director;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 游戏结束界面类，负责展示游戏结束后的弹出框，
 * 提供重新开始和返回主页的按钮。
 */
public class GameOverScene {

    /**
     * 显示游戏结束弹出框。
     * 包含游戏结束提示、重新开始按钮和返回主页按钮。
     *
     * @param stage 当前的主界面Stage对象，用于设置弹出框的父窗口。
     */
    public void showGameOverPopup(Stage stage) {
        // 创建一个新的弹出窗口
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);  // 设置为模态窗口（阻止与其他窗口的交互）
        popupStage.initStyle(StageStyle.TRANSPARENT);         // 设置透明背景
        popupStage.initOwner(stage);                          // 设置弹出窗口的拥有者为当前主界面窗口

        // 创建一个StackPane容器作为弹出窗口的根布局
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");  // 设置背景透明

        // 创建并设置“游戏结束”文本
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 50));  // 设置字体
        gameOverText.setFill(Color.GREEN);      // 设置文本颜色为绿色

        // 创建并设置“重新开始”按钮
        Button restartButton = new Button("重新开始");
        restartButton.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 30));  // 设置按钮字体
        restartButton.setTextFill(Color.WHITE);  // 设置按钮文本颜色为白色
        restartButton.setStyle(""" 
        -fx-background-color: transparent;
        -fx-border-color: white;
        -fx-border-width: 2;
        -fx-border-radius: 5;
        -fx-padding: 5 20 5 20;
        -fx-cursor: hand;
        -fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 10, 0, 0, 0);
    """);  // 设置按钮样式，包括边框、阴影和光标样式
        // 按钮点击事件：重新开始游戏
        restartButton.setOnAction(e -> {
            SoundEffect.BUTTON_CLICK.play();     // 播放按钮点击音效
            popupStage.close();                  // 关闭当前弹出窗口
            Director.getInstance().gameStart();  // 重新开始游戏
        });

        // 创建并设置“返回主页”按钮
        Button exitButton = new Button("返回主页");
        exitButton.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 30));  // 设置按钮字体
        exitButton.setTextFill(Color.WHITE);            // 设置按钮文本颜色为白色
        exitButton.setStyle(restartButton.getStyle());  // 采用与重新开始按钮相同的样式
        // 按钮点击事件：返回主页
        exitButton.setOnAction(e -> {
            SoundEffect.BUTTON_CLICK.play();    // 播放按钮点击音效
            popupStage.close();                 // 关闭当前弹出窗口
            Director.getInstance().toIndex();   // 返回主页
        });

        // 使用VBox布局管理两个按钮的垂直排列
        VBox buttonLayout = new VBox(10, restartButton, exitButton);
        buttonLayout.setStyle("-fx-alignment: center;");  // 设置按钮布局居中

        // 使用VBox布局管理游戏结束文本和按钮布局
        VBox layout = new VBox(20, gameOverText, buttonLayout);
        layout.setStyle("-fx-alignment: center;");      // 设置文本和按钮居中

        // 将布局添加到根容器中
        root.getChildren().add(layout);

        // 创建并设置弹出窗口的Scene
        Scene scene = new Scene(root, 300, 200);  // 设置弹出窗口的大小
        scene.setFill(Color.TRANSPARENT);               // 设置背景透明

        // 设置弹出窗口的Scene并显示
        popupStage.setScene(scene);

        // 将弹出窗口的位置设置为相对于主窗口居中
        popupStage.setX(stage.getX() + (stage.getWidth() - 300) / 2); // 相对于主窗口居中
        popupStage.setY(stage.getY() + (stage.getHeight() - 200) / 2); // 相对于主窗口居中

        popupStage.show();            // 显示弹出窗口
    }
}

