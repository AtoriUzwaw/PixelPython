package com.atri.scene;

import com.atri.config.AppConfig;
import com.atri.util.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class RecentRecordScene {
    public static void load(Stage stage) {
        try {
            ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
            SpringFXMLLoader loader = new SpringFXMLLoader(applicationContext);
            URL fxmlUrl = RecentRecordScene.class.getResource("/fxml/recent_record.fxml");
            if (fxmlUrl == null) {
                throw new IOException("无法找到该文件qaq");
            }
            Parent root = loader.load(fxmlUrl).load();
            stage.getScene().getStylesheets().add(Objects.requireNonNull(
                    RecentRecordScene.class.getResource("/css/recent_record_sheet.css")).toExternalForm());
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
