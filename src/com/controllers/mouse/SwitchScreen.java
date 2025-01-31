/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Handles screen switching logic in the application. Implements both `EventHandler<MouseEvent>`
 * and `Runnable` to allow screen transitions triggered by mouse clicks or programmatically.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.controllers.mouse;

import javafx.scene.input.MouseEvent; // Represents a mouse event in JavaFX.
import javafx.event.EventHandler; // Interface for handling events in JavaFX.
import com.screens.Screen; // Abstract base class for all screens in the application.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class SwitchScreen implements EventHandler<MouseEvent>, Runnable {
    Stage primaryStage; // The primary stage (window) of the application.
    Screen nextScreen; // The screen to switch to.

    public SwitchScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Sets the next screen to be displayed.
     *
     * @param nextScreen The screen to switch to.
     * @return This instance of SwitchScreen for method chaining.
     */
    public SwitchScreen setScreen(Screen nextScreen) {
        this.nextScreen = nextScreen;
        return this;
    }

    /**
     * Handles mouse click events to switch screens.
     *
     * @param event The mouse event that triggered the screen switch.
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            nextScreen.display();
        }
    }

    /**
     * Displays the next screen programmatically (without a mouse event).
     */
    @Override
    public void run() {
        nextScreen.display();
    }
}