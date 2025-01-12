package com.controllers.mouse;

import com.screens.Screen;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowScreen implements EventHandler<MouseEvent>, Runnable{
    Stage primaryStage;

    Screen popUpScreen, currentScreen;
    boolean visible, pausing;

    public ShowScreen(Stage primaryStage, Screen nextScreen) {
        this.primaryStage = primaryStage;
        this.popUpScreen = nextScreen;
        this.visible = true;
        this.pausing = true;
    }

    public ShowScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.visible = true;
        this.pausing = true;
    }

    public ShowScreen setPausing(boolean pausing) {
        this.pausing = pausing;
        return this;
    }

    public ShowScreen setPopUpScreen(Screen popUpScreen) {
        this.popUpScreen = popUpScreen;
        return this;
    }

    public ShowScreen setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
        return this;
    }

    public ShowScreen setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            popUpScreen.setVisible(visible);
            if (pausing) currentScreen.pausing(visible);
        }
    }

    // Triggered directly (Runnable)
    @Override
    public void run() {
        popUpScreen.setVisible(visible);
    }
}