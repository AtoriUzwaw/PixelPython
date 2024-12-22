package com.atri.controller;

import com.atri.service.RecentRecordService;
import com.atri.util.SoundEffect;
import com.atri.util.RoleAndSpeed;
import com.atri.view.Director;
import jakarta.annotation.Resource;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

@Controller
public class IndexController {

    @FXML
    private Canvas button1, button2, button3, recentRecordButton;
    @FXML
    private Label bestScoreLabel;

    @Resource
    RecentRecordService recentRecordService;

    // 定义常用颜色和按钮文本
    private final Color DEFAULT_BACKGROUNDCOLOR = Color.TRANSPARENT;
    private final Color HOVER_BACKGROUNDCOLOR = Color.color(0, 0.6, 0, 0.5);
    private final Color DEFAULT_TEXTCOLOR = Color.BLACK;
    private final Color HOVER_TEXTCOLOR = Color.WHITE;
    private final String BUTTON1_TEXT = "鼻涕虫", BUTTON2_TEXT = "蠕虫", BUTTON3_TEXT = "Python", RECENT_RECORD_BUTTON_TEXT = "最近记录";

    /**
     * 初始化方法，设置按钮和最佳分数标签
     */
    public void initialize() {
        // 绘制按钮
        drawButton(button1, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON1_TEXT);
        drawButton(button2, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON2_TEXT);
        drawButton(button3, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON3_TEXT);
        drawButton(recentRecordButton, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, RECENT_RECORD_BUTTON_TEXT);

        // 设置最佳分数标签字体和位置
        bestScoreLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42));
        bestScoreLabel.setStyle("-fx-padding: 3, 0, 0, 0");
        bestScoreLabel.setLayoutX(bestScoreLabel.getLayoutX() + 85);

        // 设置最佳分数
        Integer maxScore = recentRecordService.getMaxScore();
        bestScoreLabel.setText("历史最佳：" + (maxScore != null ? maxScore : "暂无记录qaq"));
    }

    /**
     * 开始游戏（鼻涕虫）
     */
    @FXML
    void clickStartGame1(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.setRoleAndSpeed(RoleAndSpeed.SLUG);
        Director.getInstance().gameStart();
    }

    /**
     * 开始游戏（蠕虫）
     */
    @FXML
    void clickStartGame2(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.setRoleAndSpeed(RoleAndSpeed.WORM);
        Director.getInstance().gameStart();
    }

    /**
     * 开始游戏（Python）
     */
    @FXML
    void clickStartGame3(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.setRoleAndSpeed(RoleAndSpeed.PYTHON);
        Director.getInstance().gameStart();
    }

    /**
     * 查看最近记录
     */
    @FXML
    void clickRecentRecord(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().toRecentRecord();
    }

    /**
     * 鼠标进入按钮区域，改变按钮样式（鼻涕虫）
     */
    @FXML
    void enterStartGame1(MouseEvent event) {
        drawButton(button1, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON1_TEXT);
    }

    /**
     * 鼠标进入按钮区域，改变按钮样式（蠕虫）
     */
    @FXML
    void enterStartGame2(MouseEvent event) {
        drawButton(button2, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON2_TEXT);
    }

    /**
     * 鼠标进入按钮区域，改变按钮样式（Python）
     */
    @FXML
    void enterStartGame3(MouseEvent event) {
        drawButton(button3, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON3_TEXT);
    }

    /**
     * 鼠标进入“最近记录”按钮区域
     */
    @FXML
    void enterRecentRecord(MouseEvent event) {
        drawButton(recentRecordButton, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, RECENT_RECORD_BUTTON_TEXT);
    }

    /**
     * 鼠标移出按钮区域，恢复默认样式（鼻涕虫）
     */
    @FXML
    void exitStartGame1(MouseEvent event) {
        drawButton(button1, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON1_TEXT);
    }

    /**
     * 鼠标移出按钮区域，恢复默认样式（蠕虫）
     */
    @FXML
    void exitStartGame2(MouseEvent event) {
        drawButton(button2, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON2_TEXT);
    }

    /**
     * 鼠标移出按钮区域，恢复默认样式（Python）
     */
    @FXML
    void exitStartGame3(MouseEvent event) {
        drawButton(button3, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON3_TEXT);
    }

    /**
     * 鼠标移出“最近记录”按钮区域，恢复默认样式
     */
    @FXML
    void exitRecentRecord(MouseEvent event) {
        drawButton(recentRecordButton, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, RECENT_RECORD_BUTTON_TEXT);
    }

    /**
     * 绘制按钮的样式
     */
    private void drawButton(Canvas button, Color backgroundColor, Color textColor, String text) {
        GraphicsContext gc = button.getGraphicsContext2D();
        gc.clearRect(0, 0, button.getWidth(), button.getHeight());
        gc.setFill(backgroundColor);
        gc.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 30, 30);
        gc.setFill(textColor);

        Text textNode = new Text(text);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42));
        double textWidth = textNode.getLayoutBounds().getWidth();
        double textHeight = textNode.getLayoutBounds().getHeight();
        double x = (button.getWidth() - textWidth) / 2 - textWidth / 2; // 水平居中
        double y = (button.getHeight() - textHeight) / 2 + textHeight;  // 垂直居中
        gc.fillText(text, x, y);
    }
}
