package com.atri.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Index {

    public static void load(Stage stage) {
        try {
            Parent root = FXMLLoader.load
                    (Objects.requireNonNull(Index.class.getResource("/fxml/index.fxml")));
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
