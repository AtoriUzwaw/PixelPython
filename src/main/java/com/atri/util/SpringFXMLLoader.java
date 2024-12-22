package com.atri.util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.net.URL;

/**
 * SpringFXMLLoader 类用于结合 Spring 和 JavaFX，加载 FXML 文件并通过 Spring 容器注入控制器。
 * 该类封装了 JavaFX 的 FXMLLoader，并配置了 Spring 的依赖注入机制。
 */
public class SpringFXMLLoader {

    // Spring 应用上下文，用于获取 Spring 管理的 Bean
    private final ApplicationContext applicationContext;

    /**
     * 构造方法，初始化 Spring 上下文。
     *
     * @param applicationContext Spring 应用上下文，用于获取 Spring 管理的 Bean。
     */
    public SpringFXMLLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 加载 FXML 文件，并使用 Spring 注入控制器。
     * 该方法返回一个 FXMLLoader 实例，通过该实例可以加载 FXML 文件并注入控制器。
     *
     * @param fxmlPath FXML 文件的路径。
     * @return 已配置 Spring 注入的 FXMLLoader 实例。
     */
    public FXMLLoader load(URL fxmlPath) {
        // 创建 FXMLLoader 实例，指定 FXML 文件的路径
        FXMLLoader loader = new FXMLLoader(fxmlPath);

        // 设置控制器工厂，使得控制器由 Spring 上下文管理
        loader.setControllerFactory(applicationContext::getBean);

        // 返回配置好的 FXMLLoader 实例
        return loader;
    }
}

