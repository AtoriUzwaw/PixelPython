package com.atri.service.impl;

import com.atri.service.RefreshService;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.springframework.stereotype.Service;

@Service
public class RefreshServiceImpl implements RefreshService {

    private AnimationTimer animationTimer;
    private Canvas canvas;
    private GraphicsContext gc;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                clearCanvas();
            }
        };
    }

    @Override
    public void start() {
        animationTimer.start();
    }

    @Override
    public void stop() {
        animationTimer.stop();
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
