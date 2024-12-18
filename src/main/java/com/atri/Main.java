package com.atri;

import com.atri.config.SpringConfiguration;
import com.atri.view.Director;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    @Autowired
    private Director director;

    private ApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // 此处必须初始化 Spring 上下文，否则无法注入 Director 依赖
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        director = context.getBean(Director.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        director.init(stage);
    }

    @Override
    public void stop() throws Exception {
        ((AnnotationConfigApplicationContext) context).close();
    }
}
