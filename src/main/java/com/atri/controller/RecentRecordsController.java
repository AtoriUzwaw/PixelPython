package com.atri.controller;

import com.atri.entity.Record;
import com.atri.service.RecentRecordService;
import com.atri.util.SoundEffect;
import com.atri.view.Director;
import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private Button backButton;

    @Resource
    RecentRecordService recentRecordService;

    /**
     * 初始化方法，在界面加载时调用。
     */
    @FXML
    public void initialize() {

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
        ObservableList<com.atri.entity.Record> records = loadRecords();
        recordsTable.setItems(records);
    }

    /**
     * 模拟加载最近记录数据。
     * @return 最近记录的列表
     */
    private ObservableList<Record> loadRecords() {
        List<com.atri.entity.Record> recentList = recentRecordService.getRecentRecordList();
        ObservableList<com.atri.entity.Record> records = FXCollections.observableArrayList();
        records.addAll(recentList);
        return FXCollections.observableArrayList(recentList);
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
