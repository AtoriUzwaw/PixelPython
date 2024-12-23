package com.atri;

import com.atri.view.Director;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

/**
 * Main 类是应用程序的入口，继承自 JavaFX 的 Application 类。
 * 它负责启动 JavaFX 应用，并初始化游戏的主界面。
 * 该类通过调用 `Director` 类的方法来设置并展示应用的主界面。
 */
public class Main extends Application {

    /**
     * 应用程序的入口点，启动 JavaFX 应用。
     * @param args 启动参数
     */
    public static void main(String[] args) {
        launch(args); // 启动 JavaFX 应用
    }

    /**
     * 初始化并展示应用的主界面。
     * @param stage JavaFX 的舞台对象，用于展示应用界面。
     * @throws Exception 如果启动过程中发生异常，抛出异常。
     */
    @Override
    public void start(Stage stage) throws Exception {
        // 使用 Director 单例类来初始化应用并展示主界面
        Director.getInstance().init(stage);
        stage.show(); // 显示窗口
    }
}

