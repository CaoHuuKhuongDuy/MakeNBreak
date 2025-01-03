package com.controllers.action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.screens.Screen;
import javafx.stage.Stage;


public class SwitchScreen implements EventHandler<ActionEvent> {
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
    public void handle(ActionEvent event) {
        nextScreen.display();
    }
}