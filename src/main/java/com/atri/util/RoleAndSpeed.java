package com.atri.util;

import lombok.Getter;

/**
 * 枚举类表示不同的角色及其对应的速度。
 * 每个角色都有一个名称和对应的移动速度。
 * 数字越小，速度越快，这里的数字会用于延迟设置（数值较小意味着更快的速度）。
 */
@Getter
public enum RoleAndSpeed {
    /**
     * 鼻涕虫角色，速度为 200。数字越小代表速度越快。
     */
    SLUG("slug", 200),

    /**
     * 蠕虫角色，速度为 150。
     */
    WORM("worm", 150),

    /**
     * Python 角色，速度为 125。
     */
    PYTHON("python", 125);

    // 角色名称
    private final String role;

    // 角色的速度
    private final int speed;

    /**
     * 构造方法，初始化角色名称和对应的速度。
     *
     * @param role 角色名称
     * @param speed 角色的移动速度
     */
    RoleAndSpeed(String role, int speed) {
        this.role = role;
        this.speed = speed;
    }
}


