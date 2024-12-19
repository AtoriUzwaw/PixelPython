package com.atri.sprite;

import com.atri.scene.GameScene;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

public abstract class Sprite {

    Image image;
    @Getter
    @Setter
    double x;
    @Getter
    @Setter
    double y;
    double width;
    double height;
    GameScene gameScene;

    public Sprite(Image image, double x, double y, double width, double height,
                  GameScene gameScene) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameScene = gameScene;
    }

    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Sprite() {}

    public void paint(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);
    }

    public Rectangle2D rectangle2D() {
        return new Rectangle2D(x, y, width, height);
    }

    public void destroy() {};

}
