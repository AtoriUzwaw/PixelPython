package com.atri;

import com.atri.view.Director;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Director.getInstance().init(stage);
    }
}
