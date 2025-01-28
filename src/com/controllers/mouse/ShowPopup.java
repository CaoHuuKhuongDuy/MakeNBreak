package com.controllers.mouse;

import com.screens.Screen;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowPopup implements EventHandler<MouseEvent>, Runnable{
    Stage primaryStage;

    Screen popUpScreen, currentScreen;
    boolean visible, pausing;

    public ShowPopup(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.visible = true;
        this.pausing = true;
    }

    public ShowPopup setPausing(boolean pausing) {
        this.pausing = pausing;
        return this;
    }

    public ShowPopup setPopUpScreen(Screen popUpScreen) {
        this.popUpScreen = popUpScreen;
        return this;
    }

    public ShowPopup setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
        return this;
    }

    public ShowPopup setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            popUpScreen.setVisible(visible);
            if (visible) {
                popUpScreen.toFront();
            }
            if (pausing) currentScreen.pausing(visible);
        }
    }

    // Triggered directly (Runnable)
    @Override
    public void run() {
        if (visible) {
            popUpScreen.toFront();
        }
        popUpScreen.setVisible(visible);
    }
}