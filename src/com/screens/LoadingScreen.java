/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the loading screen of the application. Displays a loading bar and
 * transitions to the game screen once the loading is complete.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.commons.Coordinate; // Represents a coordinate in the game grid.
import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.models.components.LoadingBar; // Represents a loading bar component.
import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class LoadingScreen extends Screen {

    private SwitchScreen switchScreen; // Handles screen switching logic.
    private LoadingBar loadingBar; // Represents the loading bar component.

    /**
     * Constructs a LoadingScreen with the specified primary stage.
     *
     * @param primaryStage The primary stage (window) of the application.
     */
    public LoadingScreen(Stage primaryStage) {
        super(primaryStage);
        this.loadingBar = new LoadingBar(new Coordinate(256, 646), 512, 48);
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

        // Add the title image.
        Image titleImage = new Image(Globals.getResource("/resources/assets/images/titleSlanted.png"));
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setLayoutY(30);

        // Add the instruction text image.
        ImageView instructionText = new ImageView(new Image(Globals.getResource("/resources/assets/images/text.png")));
        instructionText.setLayoutX(154);
        instructionText.setLayoutY(595);

        // Configure and add the loading bar.
        loadingBar.configureTimeline(() -> {
            switchScreen.setScreen(new GameScreen(primaryStage)).run();
        });

        // Add all components to the screen.
        this.getChildren().addAll(loadingBar.getProgressBar(), titleImageView, instructionText);

        // Set the current scene to this screen and start the loading animation.
        this.primaryStage.getScene().setRoot(this);
        loadingBar.startAnimation();
    }
}