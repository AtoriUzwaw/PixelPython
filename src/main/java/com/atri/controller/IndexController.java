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

/**
 * 控制器类，用于处理主页界面上的交互逻辑。
 * 包括按钮的点击事件、鼠标悬停和离开事件的处理以及界面初始化。
 */
@Controller
public class IndexController {

    // FXML 注解绑定界面控件
    @FXML
    private Canvas button1, button2, button3, recentRecordButton;  // 游戏选择按钮
    @FXML
    private Label bestScoreLabel;                                  // 显示最佳成绩的标签

    @Resource
    private RecentRecordService recentRecordService;                // 用于获取成绩记录的服务

    // 定义常用颜色和按钮文本
    private final Color DEFAULT_BACKGROUNDCOLOR = Color.TRANSPARENT;  // 默认背景颜色
    private final Color HOVER_BACKGROUNDCOLOR = Color.color(0, 0.6, 0, 0.5);  // 鼠标悬停背景颜色
    private final Color DEFAULT_TEXTCOLOR = Color.BLACK;    // 默认文本颜色
    private final Color HOVER_TEXTCOLOR = Color.WHITE;      // 鼠标悬停文本颜色
    private final String BUTTON1_TEXT = "鼻涕虫";            // 鼠标点击“鼻涕虫”按钮时显示的文本
    private final String BUTTON2_TEXT = "蠕虫";              // 鼠标点击“蠕虫”按钮时显示的文本
    private final String BUTTON3_TEXT = "Python";                // 鼠标点击“Python”按钮时显示的文本
    private final String RECENT_RECORD_BUTTON_TEXT = "最近记录";  // 鼠标点击“最近记录”按钮时显示的文本

    /**
     * 初始化方法，设置按钮和最佳分数标签。
     * 该方法在界面加载时被调用。
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

        // 设置最佳分数，获取最大分数并显示
        Integer maxScore = recentRecordService.getMaxScore();
        bestScoreLabel.setText("历史最佳：" + (maxScore != null ? maxScore : "暂无记录qaq"));
    }

    /**
     * 鼠标点击“鼻涕虫”按钮，开始对应的游戏。
     * 设置角色为“鼻涕虫”并启动游戏。
     */
    @FXML
    void clickStartGame1(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.setRoleAndSpeed(RoleAndSpeed.SLUG);  // 设置角色为鼻涕虫
        Director.getInstance().gameStart();  // 启动游戏
    }

    /**
     * 鼠标点击“蠕虫”按钮，开始对应的游戏。
     * 设置角色为“蠕虫”并启动游戏。
     */
    @FXML
    void clickStartGame2(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.setRoleAndSpeed(RoleAndSpeed.WORM);  // 设置角色为蠕虫
        Director.getInstance().gameStart();  // 启动游戏
    }

    /**
     * 鼠标点击“Python”按钮，开始对应的游戏。
     * 设置角色为“Python”并启动游戏。
     */
    @FXML
    void clickStartGame3(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.setRoleAndSpeed(RoleAndSpeed.PYTHON);  // 设置角色为Python
        Director.getInstance().gameStart();  // 启动游戏
    }

    /**
     * 鼠标点击“最近记录”按钮，查看最近的游戏记录。
     */
    @FXML
    void clickRecentRecord(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().toRecentRecord();  // 跳转到最近记录页面
    }

    /**
     * 鼠标进入“鼻涕虫”按钮区域，改变按钮样式。
     * 鼠标悬停时背景变色，文本变为白色。
     */
    @FXML
    void enterStartGame1(MouseEvent event) {
        drawButton(button1, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON1_TEXT);
    }

    /**
     * 鼠标进入“蠕虫”按钮区域，改变按钮样式。
     * 鼠标悬停时背景变色，文本变为白色。
     */
    @FXML
    void enterStartGame2(MouseEvent event) {
        drawButton(button2, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON2_TEXT);
    }

    /**
     * 鼠标进入“Python”按钮区域，改变按钮样式。
     * 鼠标悬停时背景变色，文本变为白色。
     */
    @FXML
    void enterStartGame3(MouseEvent event) {
        drawButton(button3, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON3_TEXT);
    }

    /**
     * 鼠标进入“最近记录”按钮区域，改变按钮样式。
     * 鼠标悬停时背景变色，文本变为白色。
     */
    @FXML
    void enterRecentRecord(MouseEvent event) {
        drawButton(recentRecordButton, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, RECENT_RECORD_BUTTON_TEXT);
    }

    /**
     * 鼠标移出“鼻涕虫”按钮区域，恢复默认样式。
     */
    @FXML
    void exitStartGame1(MouseEvent event) {
        drawButton(button1, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON1_TEXT);
    }

    /**
     * 鼠标移出“蠕虫”按钮区域，恢复默认样式。
     */
    @FXML
    void exitStartGame2(MouseEvent event) {
        drawButton(button2, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON2_TEXT);
    }

    /**
     * 鼠标移出“Python”按钮区域，恢复默认样式。
     */
    @FXML
    void exitStartGame3(MouseEvent event) {
        drawButton(button3, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON3_TEXT);
    }

    /**
     * 鼠标移出“最近记录”按钮区域，恢复默认样式。
     */
    @FXML
    void exitRecentRecord(MouseEvent event) {
        drawButton(recentRecordButton, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, RECENT_RECORD_BUTTON_TEXT);
    }

    /**
     * 绘制按钮的样式
     * 设置按钮的背景颜色、文本颜色和按钮文本，确保按钮内容居中显示。
     *
     * @param button          当前按钮
     * @param backgroundColor 背景颜色
     * @param textColor       文本颜色
     * @param text            按钮文本
     */
    private void drawButton(Canvas button, Color backgroundColor, Color textColor, String text) {
        GraphicsContext gc = button.getGraphicsContext2D();
        gc.clearRect(0, 0, button.getWidth(), button.getHeight());  // 清除之前绘制的内容
        gc.setFill(backgroundColor);  // 设置背景色
        gc.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 30, 30);  // 绘制圆角矩形
        gc.setFill(textColor);  // 设置文本色

        Text textNode = new Text(text);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream("/font/Silver.ttf"), 42));  // 设置字体
        double textWidth = textNode.getLayoutBounds().getWidth();        // 获取文本宽度
        double textHeight = textNode.getLayoutBounds().getHeight();      // 获取文本高度
        double x = (button.getWidth() - textWidth) / 2 - textWidth / 2;  // 水平居中
        double y = (button.getHeight() - textHeight) / 2 + textHeight;   // 垂直居中
        gc.fillText(text, x, y);  // 绘制文本
    }
}

