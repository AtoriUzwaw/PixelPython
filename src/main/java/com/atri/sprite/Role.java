package com.atri.sprite;

import com.atri.util.Direction;
import lombok.Getter;
import lombok.Setter;

public abstract class Role extends Sprite{

    @Getter
    @Setter
    boolean alive = true;
    @Getter
    Direction direction;
    double speed;

    public Role(double x, double y, double width, double height,
                Direction direction, double speed) {
        super(x, y, width, height);
        this.direction = direction;
        this.speed = speed;
    }

    public Role(double x, double y, Direction direction, double speed) {
        super(x, y);
        this.direction = direction;
        this.speed = speed;
    }

    public Role(boolean alive) {
        super();
        this.alive = alive;
    }

    public abstract void move();

    public abstract boolean collisionCheck(Sprite sprite);
}
