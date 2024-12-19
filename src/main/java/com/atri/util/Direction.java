package com.atri.util;

import lombok.Getter;

@Getter
public enum Direction {
    UP(0, -10),
    DOWN(0, 10),
    LEFT(-10, 0),
    RIGHT(10, 0);

    private final int x, y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
