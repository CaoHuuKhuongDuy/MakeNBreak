package com.screens;

import com.controllers.mouse.SwitchScreen;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class MainScreen extends Screen {

    private SwitchScreen switchScreen;

    public MainScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        this.getChildren().clear();

        Button playGameButton = new Button("Play Game");

        playGameButton.setOnMouseClicked(this.switchScreen.setScreen(new GameScreen(primaryStage)));
        playGameButton.setLayoutX(800);
        playGameButton.setLayoutY(400);

        this.getChildren().add(playGameButton);

        this.primaryStage.getScene().setRoot(this);
    }
}
