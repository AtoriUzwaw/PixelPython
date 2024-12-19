package com.atri.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.Random;

public class Food extends Role{
    int snackX;
    int snackY;

    public Food(boolean alive) {
        super(alive);
    }

    public void snack(double width, double height) {
        Random random = new Random();
        this.snackX = random.nextInt((int) 720 / 10) * 10;
        this.snackY = random.nextInt((int) 480 / 10) * 10;
    }

    public void drawFood(GraphicsContext gc) {
        // 每个方格的大小
        int GRID_SIZE = 10;
        gc.setFont(Font.font(25));
        gc.fillText("✿", snackX + (double) GRID_SIZE / 4, snackY + GRID_SIZE / 1.5);
    }

    @Override
    public void move() {

    }

    @Override
    public boolean collisionCheck(Sprite sprite) {
        return false;
    }
}
