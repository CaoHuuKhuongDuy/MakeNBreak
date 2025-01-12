package com.controllers.mouse;

import com.screens.Screen;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowScreen implements EventHandler<MouseEvent>, Runnable{
    Stage primaryStage;
    Screen screen;
    boolean visible;

    public ShowScreen(Stage primaryStage, Screen nextScreen) {
        this.primaryStage = primaryStage;
        this.screen = nextScreen;
        visible = true;
    }

    public ShowScreen(Stage primaryStage) {
        visible = true;
        this.primaryStage = primaryStage;
    }

    public ShowScreen setScreen(Screen nextScreen) {
        this.screen = nextScreen;
        return this;
    }

    public ShowScreen setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            screen.setVisible(visible);
        }
    }

    // Triggered directly (Runnable)
    @Override
    public void run() {
        screen.setVisible(visible);
    }
}