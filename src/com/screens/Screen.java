/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Abstract base class for all screens in the application. Provides common functionality
 * such as setting up a background image and initializing a canvas.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import javafx.scene.canvas.Canvas; // A node used for drawing graphics.
import javafx.scene.layout.Pane; // A base class for layout containers.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.layout.Background; // Represents the background of a layout container.
import javafx.scene.layout.BackgroundImage; // Represents an image used as a background.
import javafx.scene.layout.BackgroundPosition; // Specifies the position of a background image.
import javafx.scene.layout.BackgroundRepeat; // Specifies how a background image is repeated.

public abstract class Screen extends Pane {
    protected String title; // The title of the screen.
    protected Stage primaryStage; // The primary stage (window) of the application.
    protected Canvas canvas; // A canvas for drawing graphics.

    public Screen(Stage primaryStage) {
        this(primaryStage, "");
    }

    public Screen(Stage primaryStage, String title) {
        this.primaryStage = primaryStage;
        this.title = title;
        setupBackground("/resources/assets/images/background.png"); // Set up the background image.
        setPrefSize(Globals.DEFAULT_WIDTH, Globals.DEFAULT_HEIGHT); // Set the preferred size of the screen.
        this.canvas = new Canvas(Globals.DEFAULT_WIDTH, Globals.DEFAULT_HEIGHT); // Initialize the canvas.
    }

    /**
     * Sets up the background image for the screen.
     *
     * @param imagePath The path to the background image.
     */
    protected void setupBackground(String imagePath) {
        imagePath = Globals.getResource(imagePath);
        Image backgroundImage = new Image(imagePath);

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                null
        );

        setBackground(new Background(bgImage));
    }

    public void pausing(boolean pausing) {} // Placeholder for pausing functionality.
    public abstract void display(); // Abstract method to display the screen.
    public abstract void initHandlers(); // Abstract method to initialize event handlers.
}