package com.atri.service.impl;

import com.atri.service.KeyProcessService;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Service;

@Service
public class KeyProcessServiceImpl implements KeyProcessService {

    private boolean running = false;

    @Override
    public void handle(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (KeyCode.SPACE.equals(keyCode)) toggleGameState();
    }

    @Override
    public void toggleGameState() {
        running = !running;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean b) {
        this.running = b;
    }
}
