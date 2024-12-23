package com.atri.sprite;

import com.atri.util.Direction;
import lombok.Getter;
import lombok.Setter;

/**
 * Role 类是所有角色的基类，继承自 Sprite 类。
 * 该类包含角色的存活状态、移动方向，并定义了角色的移动行为（抽象方法）。
 */
@Getter
public abstract class Role extends Sprite {

    @Setter
    boolean alive = true;  // 角色是否存活，默认为存活
    Direction direction;    // 角色的移动方向

    /**
     * 构造方法：根据指定的方向初始化角色。
     *
     * @param direction 角色的移动方向
     */
    public Role(Direction direction) {
        this.direction = direction;
    }

    /**
     * 构造方法：根据角色的存活状态初始化角色。
     *
     * @param alive 角色的存活状态（true 表示存活，false 表示死亡）
     */
    public Role(boolean alive) {
        super();
        this.alive = alive;
    }

    /**
     * 抽象方法：定义角色的移动行为。
     * 该方法由子类实现，决定不同角色如何移动。
     */
    public abstract void move();
}


