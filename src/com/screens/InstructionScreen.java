/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the instruction screen of the application. Displays game instructions
 * and provides a button to return to the main screen.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.models.components.CustomButton; // Represents a custom button in the UI.
import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class InstructionScreen extends Screen {

    private SwitchScreen switchScreen; // Handles screen switching logic.

    /**
     * Constructs an InstructionScreen with the specified primary stage.
     *
     * @param primaryStage The primary stage (window) of the application.
     */
    public InstructionScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
        setupBackground("/resources/assets/images/instructions.png"); // Set up the background image.
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

        // Create and configure the exit button.
        CustomButton exitButton = new CustomButton("", "/resources/assets/images/Button/Icon_Cross.png");
        exitButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));

        // Position the exit button.
        exitButton.setLayoutX(973);
        exitButton.setLayoutY(20);

        // Add the exit button to the screen.
        this.getChildren().addAll(exitButton);

        // Set the current scene to this screen.
        this.primaryStage.getScene().setRoot(this);
    }
}