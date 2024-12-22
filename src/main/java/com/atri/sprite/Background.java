package com.atri.sprite;

import javafx.scene.image.Image;


/**
 * Background 类表示游戏中的背景图像，继承自 Sprite 类。
 * 该类负责加载并显示背景图像。
 */
public class Background extends Sprite {

    /**
     * 构造方法：初始化背景图像并设置其位置与大小。
     * 背景图像会设置为 800x600 的尺寸，位置为 (0, 0)。
     */
    public Background() {
        // 调用父类构造方法设置背景图像，位置和尺寸
        super(new Image("/image/background.png"), 0, 0, 800, 600);
    }
}

