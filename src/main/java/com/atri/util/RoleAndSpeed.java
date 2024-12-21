package com.atri.util;

import lombok.Getter;

@Getter
public enum RoleAndSpeed {
    SLUG("slug",200),
    WORM("worm",150),
    PYTHON("python",125);

    private final String role;
    private final int speed;

    RoleAndSpeed(String role, int speed) {
        this.role = role;
        this.speed = speed;
    }
}
