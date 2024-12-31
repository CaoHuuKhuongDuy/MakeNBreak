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
        // Clear any existing children in case the method is called multiple times
        layout.getChildren().clear();

        // Create the "Play Game" button
        Button playGameButton = new Button("Play Game");

        // Add action for the button (optional)
        playGameButton.setOnMouseClicked(this.switchScreen.handler(new GameScreen(primaryStage)));

        // Set the button position to the center of the screen
        playGameButton.setLayoutX(800);
        playGameButton.setLayoutY(400);

        // Add the button to the layout
        layout.getChildren().add(playGameButton);

        // Set the scene with the updated layout
        this.primaryStage.getScene().setRoot(layout);
    }
}
