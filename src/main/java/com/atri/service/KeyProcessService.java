package com.atri.service;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface KeyProcessService extends EventHandler<KeyEvent> {
    void toggleGameState();
    boolean isRunning();
    void setRunning(boolean b);
}
