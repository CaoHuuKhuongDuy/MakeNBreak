package com.controllers.mouse;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import com.screens.Screen;
import javafx.stage.Stage;


public class SwitchScreen implements EventHandler<MouseEvent> {
    Stage primaryStage;
    Screen nextScreen;

    public SwitchScreen(Stage primaryStage, Screen nextScreen) {
        this.primaryStage = primaryStage;
        this.nextScreen = nextScreen;
    }

    public SwitchScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public SwitchScreen setScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            nextScreen.display();
        }
    }
}
