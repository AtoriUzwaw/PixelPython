package com.atri.util;

import lombok.Getter;

@Getter
public enum Speed {
    SLUG(200),
    WORM(150),
    PYTHON(125);

    private final int speed;

    Speed(int speed) {
        this.speed = speed;
    }
}
