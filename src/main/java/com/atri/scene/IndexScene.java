package com.atri.scene;

import com.atri.config.AppConfig;
import com.atri.util.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;

/**
 * 主页界面
 */
public class IndexScene {

    public static void load(Stage stage) {
        try {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
            SpringFXMLLoader loader = new SpringFXMLLoader(applicationContext);
            URL fxmlUrl = RecentRecordScene.class.getResource("/fxml/index.fxml");
            if (fxmlUrl == null) {
                throw new IOException("无法找到该文件qaq");
            }
            Parent root = loader.load(fxmlUrl).load();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
