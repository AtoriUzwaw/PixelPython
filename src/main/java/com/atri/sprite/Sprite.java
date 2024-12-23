package com.atri.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

/**
 * Sprite 类代表了游戏中的一个可绘制对象，例如角色、物品等。
 * 它包含了图像、位置和大小等属性，并提供了绘制图像的方法。
 */
public abstract class Sprite {

    Image image;    // 角色的图像
    @Getter
    @Setter
    double x;       // 角色的 x 坐标
    @Getter
    @Setter
    double y;       // 角色的 y 坐标
    double width;   // 角色的宽度
    double height;  // 角色的高度

    /**
     * 构造方法：初始化角色图像、位置和大小。
     *
     * @param image 角色的图像
     * @param x 角色的 x 坐标
     * @param y 角色的 y 坐标
     * @param width 角色的宽度
     * @param height 角色的高度
     */
    public Sprite(Image image, double x, double y, double width, double height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // 默认构造方法
    public Sprite() {}

    /**
     * 绘制角色图像。
     * 使用指定的图形上下文（GraphicsContext）在 (x, y) 坐标位置绘制角色图像。
     *
     * @param gc 图形上下文，用于绘制图像
     */
    public void paint(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);  // 在指定位置绘制图像
    }
}


