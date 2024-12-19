package com.atri.controller;

import com.atri.util.SoundEffect;
import com.atri.view.Director;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IndexController {

    @FXML
    private Canvas button1;

    @FXML
    private Canvas button2;

    @FXML
    private Canvas button3;

    private final Color DEFAULT_BACKGROUNDCOLOR = Color.TRANSPARENT;
    private final Color HOVER_BACKGROUNDCOLOR = Color.color(0, 0.6, 0, 0.5);
    private final Color DEFAULT_TEXTCOLOR = Color.BLACK;
    private final Color HOVER_TEXTCOLOR = Color.WHITE;
    private final String BUTTON1_TEXT = "鼻涕虫";
    private final String BUTTON2_TEXT = "蠕虫";
    private final String BUTTON3_TEXT = "Python";

    public void initialize() {
        drawButton(button1, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON1_TEXT);
        drawButton(button2, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON2_TEXT);
        drawButton(button3, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON3_TEXT);
    }

    @FXML
    void clickStartGame1(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().gameStart();
    }

    @FXML
    void clickStartGame2(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().gameStart();
    }

    @FXML
    void clickStartGame3(MouseEvent event) {
        SoundEffect.BUTTON_CLICK.play();
        Director.getInstance().gameStart();
    }

    @FXML
    void enterStartGame1(MouseEvent event) {
        drawButton(button1, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON1_TEXT);
    }

    @FXML
    void enterStartGame2(MouseEvent event) {
        drawButton(button2, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON2_TEXT);
    }

    @FXML
    void enterStartGame3(MouseEvent event) {
        drawButton(button3, HOVER_BACKGROUNDCOLOR, HOVER_TEXTCOLOR, BUTTON3_TEXT);
    }

    @FXML
    void exitStartGame1(MouseEvent event) {
        drawButton(button1, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON1_TEXT);
    }

    @FXML
    void exitStartGame2(MouseEvent event) {
        drawButton(button2, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON2_TEXT);
    }

    @FXML
    void exitStartGame3(MouseEvent event) {
        drawButton(button3, DEFAULT_BACKGROUNDCOLOR, DEFAULT_TEXTCOLOR, BUTTON3_TEXT);
    }

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
