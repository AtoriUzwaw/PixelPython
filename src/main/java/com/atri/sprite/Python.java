package com.atri.sprite;

import com.atri.util.Direction;

public class Python extends Role{

    public Python(double x, double y, Direction direction, double speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void move() {

    }

    @Override
    public boolean collisionCheck(Sprite sprite) {
        return false;
    }
}
