package com.atri.controller;

import com.atri.entity.Record;
import com.atri.service.RecentRecordService;
import com.atri.util.SoundEffect;
import com.atri.view.Director;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RecentRecordsController {

    @FXML
    private TableView<com.atri.entity.Record> recordsTable;

    @FXML
    private TableColumn<com.atri.entity.Record, Integer> indexColumn;

    @FXML
    private TableColumn<Record, String> roleColumn;

    @FXML
    private TableColumn<com.atri.entity.Record, Integer> scoreColumn;

    @FXML
    private TableColumn<com.atri.entity.Record, LocalDateTime> dateColumn;

    @FXML
    private Label title;

    @FXML
    private Button backButton;

    @FXML
    private Button clearButton;

    @Resource
    RecentRecordService recentRecordService;

    /**
     * 初始化方法，在界面加载时调用。
     */
    @FXML
    public void initialize() {
        // 设置表头样式
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 32));
        title.setStyle("-fx-text-fill: #92ae51");

        // 设置每一列的样式
        indexColumn.setStyle("-fx-font-weight: bold; -fx-font-family: 'Silver'; -fx-font-size: 24px;");
        roleColumn.setStyle("-fx-font-weight: bold; -fx-font-family: 'Silver'; -fx-font-size: 24px;");
        scoreColumn.setStyle("-fx-font-weight: bold; -fx-font-family: 'Silver'; -fx-font-size: 24px;");
        dateColumn.setStyle("-fx-font-weight: bold; -fx-font-family: 'Silver'; -fx-font-size: 24px;");

        // 设置列的数据映射
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        // 自定义一个 CellFactory，负责格式化显示日期
        dateColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Record, LocalDateTime> call(TableColumn<com.atri.entity.Record, LocalDateTime> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            // 将 LocalDateTime 格式化，便于查看
                            setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                    }
                };
            }
        });

        // 加载数据到表格
        loadRecords();

    }


    private void loadRecords() {
        List<com.atri.entity.Record> recentList = recentRecordService.getRecentRecordList();
        ObservableList<com.atri.entity.Record> records = FXCollections.observableArrayList();
        if (recentList.isEmpty()) {
            title.setText("没有最近记录qaq");
        } else {
            records.addAll(recentList);
            recordsTable.setItems(records);
        }
    }

    @FXML
    private void onClearButtonClick() {
        SoundEffect.BUTTON_CLICK.play();

        // 创建一个确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认");
        alert.setHeaderText("您确定要清除记录吗？");
        alert.setContentText("此操作将无法恢复。");
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/image/icon.png")))));

        // 创建确认和取消按钮
        ButtonType confirmButton = new ButtonType("确认");
        ButtonType cancelButton = new ButtonType("取消");

        // 设置按钮
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/css/confirmation_dialog.css")).toExternalForm());

        // 显示弹窗并等待用户的选择
        Optional<ButtonType> result = alert.showAndWait();

        // 点击确认按钮，执行删除相关逻辑
        if (result.isPresent() && result.get() == confirmButton) {
            SoundEffect.BUTTON_CLICK.play();
            // 清除记录
            clearRecords();
            // 刷新界面
            Director.getInstance().toRecentRecord();
        }
        if (result.isPresent() && result.get() == cancelButton) {
            SoundEffect.BUTTON_CLICK.play();
        }
    }

    /**
     * 清除记录
     */
    private void clearRecords() {
        recentRecordService.clearRecord();
    }

    /**
     * 返回按钮点击事件。
     */
    @FXML
    private void onBackButtonClick() {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().toIndex();
    }
}
