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

/**
 * 控制器类，用于处理“最近记录”界面的逻辑。
 * 该类与Fxml界面关联，负责数据显示、交互逻辑及操作事件的处理。
 */
@Controller
public class RecentRecordsController {

    // FXML 注解绑定界面控件
    @FXML
    private TableView<com.atri.entity.Record> recordsTable;  // 显示成绩记录的表格
    @FXML
    private TableColumn<com.atri.entity.Record, Integer> indexColumn;  // 表格中的索引列
    @FXML
    private TableColumn<Record, String> roleColumn;  // 角色列
    @FXML
    private TableColumn<com.atri.entity.Record, Integer> scoreColumn;  // 分数列
    @FXML
    private TableColumn<com.atri.entity.Record, LocalDateTime> dateColumn;  // 日期列
    @FXML
    private Label title;  // 标题标签

    @Resource
    private RecentRecordService recentRecordService;  // 服务层，用于获取和管理成绩记录

    /**
     * 初始化方法，设置界面显示内容。
     * 被FXML调用，在界面加载时执行。
     */
    @FXML
    public void initialize() {
        // 设置标题字体和颜色
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 32));
        title.setStyle("-fx-text-fill: #92ae51");

        // 设置表格列的字体样式
        setColumnStyles();

        // 设置表格列的数据绑定
        setColumnCellValueFactories();

        // 设置日期列的显示格式
        setDateColumnCellFactory();

        // 加载最近记录
        loadRecords();
    }

    /**
     * 设置表格列的样式。
     * 该方法为表格列设置了统一的字体样式。
     */
    private void setColumnStyles() {
        String style = "-fx-font-weight: bold; -fx-font-family: 'Silver'; -fx-font-size: 24px;";
        indexColumn.setStyle(style);
        roleColumn.setStyle(style);
        scoreColumn.setStyle(style);
        dateColumn.setStyle(style);
    }

    /**
     * 设置表格列的数据绑定。
     * 该方法将表格的每一列与 `Record` 类中的属性进行绑定。
     */
    private void setColumnCellValueFactories() {
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    }

    /**
     * 设置日期列的格式化显示。
     * 该方法将 `LocalDateTime` 类型的日期格式化为 `yyyy-MM-dd HH:mm:ss`。
     */
    private void setDateColumnCellFactory() {
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
                            // 格式化日期为 "yyyy-MM-dd HH:mm:ss"
                            setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                    }
                };
            }
        });
    }

    /**
     * 加载记录到表格。
     * 从 `recentRecordService` 获取最近记录并显示在表格中。
     */
    private void loadRecords() {
        List<com.atri.entity.Record> recentList = recentRecordService.getRecentRecordList();
        ObservableList<com.atri.entity.Record> records = FXCollections.observableArrayList();
        if (recentList.isEmpty()) {
            title.setText("没有最近记录qaq");  // 若没有记录，显示提示信息
        } else {
            records.addAll(recentList);
            recordsTable.setItems(records);  // 将记录添加到表格中
        }
    }

    /**
     * 清除记录按钮点击事件。
     * 弹出确认对话框，用户确认后清除记录。
     */
    @FXML
    private void onClearButtonClick() {
        SoundEffect.BUTTON_CLICK.play();

        // 创建确认对话框
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认");
        alert.setHeaderText("您确定要清除记录吗？");
        alert.setContentText("此操作将无法恢复。");
        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/image/icon.png")))));  // 添加图标

        // 设置按钮
        ButtonType confirmButton = new ButtonType("确认");
        ButtonType cancelButton = new ButtonType("取消");
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/css/confirmation_dialog.css")).toExternalForm());

        // 显示并处理用户选择
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirmButton) {
            SoundEffect.BUTTON_CLICK.play();
            // 执行清除操作
            clearRecords();
            Director.getInstance().toRecentRecord(); // 刷新页面
        }
    }

    /**
     * 清除所有记录。
     * 通过 `recentRecordService` 调用清除记录的操作。
     */
    private void clearRecords() {
        recentRecordService.clearRecord();
    }

    /**
     * 返回按钮点击事件，返回主页面。
     * 当用户点击返回按钮时，返回到主页面。
     */
    @FXML
    private void onBackButtonClick() {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().toIndex();
    }
}


