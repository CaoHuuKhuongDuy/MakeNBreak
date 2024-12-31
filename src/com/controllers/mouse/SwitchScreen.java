package com.controllers.mouse;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import com.screens.Screen;
import javafx.stage.Stage;
import java.util.function.Consumer;


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

    public void setScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
    }

    public SwitchScreen handler(Screen nextScreen) {
        return new SwitchScreen(this.primaryStage, nextScreen);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            nextScreen.display();
        }
    }
}
