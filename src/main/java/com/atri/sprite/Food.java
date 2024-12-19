package com.atri.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.util.Random;

public class Food extends Role{
    int snackX;
    int snackY;
    int GRID_SIZE = 20;

    public Food(boolean alive) {
        super(alive);
    }

    public void snack(LinkedList<Python.Segment> body) {
        Random random = new Random();
        for (Python.Segment segment : body) {
            int snackX = random.nextInt(600 / GRID_SIZE) * GRID_SIZE;
            int snackY = random.nextInt(400 / GRID_SIZE) * GRID_SIZE;

            if (snackX != segment.x * GRID_SIZE) this.snackX = snackX;
            if (snackY != segment.y * GRID_SIZE) this.snackY = snackY;
        }
    }

    public void drawFood(GraphicsContext gc) {
        gc.setFont(Font.font(24));
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
