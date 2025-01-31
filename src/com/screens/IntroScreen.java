/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the introductory screen of the application. Displays the title,
 * a start button, and a quit button.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import com.models.components.CustomButton; // Represents a custom button in the UI.
import javafx.application.Platform; // Provides platform-specific functionality.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class IntroScreen extends Screen {

    private SwitchScreen switchScreen; // Handles screen switching logic.

    public IntroScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {
        // Initialize the screen switching logic.
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        // Clear the screen and set up the UI elements.
        this.getChildren().clear();

        // Load and display the title image.
        Image titleImage = new Image(Globals.getResource("/resources/assets/images/titleSlanted.png"));
        ImageView titleImageView = new ImageView(titleImage);

        // Create and configure the start button.
        CustomButton startButton = new CustomButton("START", "/resources/assets/images/Button/yellowButton.png");

        // Create and configure the quit button.
        CustomButton quitButton = new CustomButton("QUIT", "/resources/assets/images/Button/redButton.png");

        // Set the action for the quit button to exit the application.
        quitButton.setOnAction(event -> {
            Platform.exit();
        });

        // Set the action for the start button to switch to the instruction screen.
        startButton.setOnMouseClicked(this.switchScreen.setScreen(new InstructionScreen(primaryStage)));

        // Position the title image and buttons on the screen.
        titleImageView.setLayoutX(0);
        titleImageView.setLayoutY(30);

        startButton.setLayoutX(408);
        startButton.setLayoutY(573);

        quitButton.setLayoutX(408);
        quitButton.setLayoutY(651);

        // Add the title image and buttons to the screen.
        this.getChildren().addAll(titleImageView, startButton, quitButton);
        this.primaryStage.getScene().setRoot(this);
    }
}