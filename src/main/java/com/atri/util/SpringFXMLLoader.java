package com.atri.util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URL;

public class SpringFXMLLoader {

    private final ApplicationContext applicationContext;

    public SpringFXMLLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public FXMLLoader load(URL fxmlPath) {
        FXMLLoader loader = new FXMLLoader(fxmlPath);
        loader.setControllerFactory(applicationContext::getBean); // 使用 Spring 注入控制器
        return loader;
    }
}
