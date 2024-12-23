package com.atri.util;

import lombok.Getter;

/**
 * 枚举类表示游戏中四个方向。
 * 每个方向都包含对应的坐标偏移量（x, y）。
 */
@Getter
public enum Direction {
    /**
     * 向上移动，x 不变，y 减小。
     */
    UP(0, -10),

    /**
     * 向下移动，x 不变，y 增加。
     */
    DOWN(0, 10),

    /**
     * 向左移动，x 减小，y 不变。
     */
    LEFT(-10, 0),

    /**
     * 向右移动，x 增加，y 不变。
     */
    RIGHT(10, 0);

    // x 和 y 分别表示水平和垂直方向上的偏移量
    private final int x, y;

    /**
     * 构造方法，初始化方向对应的 x 和 y 偏移量。
     *
     * @param x 水平方向的偏移量
     * @param y 垂直方向的偏移量
     */
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

