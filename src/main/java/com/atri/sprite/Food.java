package com.atri.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.util.Random;

public class Food extends Role {
    int snackX;
    int snackY;
    int GRID_SIZE = 20;

    public Food(boolean alive) {
        super(alive);
    }

    public void snack(LinkedList<Python.Segment> body) {
        Random random = new Random();
        boolean validPosition = false;
        while (!validPosition) {
            super.x = random.nextInt(0, 30);
            // 这里不能改成（0，20）我也不知道为啥，但是现在能正常跑，就不要动啦~
            super.y = random.nextInt(1, 21);
            int snackX = (int) x * GRID_SIZE;
            int snackY = (int) y * GRID_SIZE;
            validPosition = true;
            // 520, 0
            for (Python.Segment segment : body) {
                if (snackX == segment.x * GRID_SIZE && snackY == segment.y * GRID_SIZE) {
                    validPosition = false;
                    break;
                }
            }
            this.snackX = snackX;
            this.snackY = snackY;
            System.out.println(x + ", " + y);
        }
    }

    public void drawFood(GraphicsContext gc) {
        gc.setFont(Font.font(24));
        // 微调 “食物” 的像素位置
        gc.fillText("✿", snackX - 2, snackY - 1);
    }

    @Override
    public void move() {

    }

    @Override
    public boolean collisionCheck(Sprite sprite) {
        return false;
    }
}
