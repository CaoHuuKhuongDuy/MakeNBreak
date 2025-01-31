/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Handles the display and visibility of pop-up screens in the application.
 * Manages the visibility of the pop-up screen and optionally pauses the current screen.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.controllers.mouse;

import com.screens.Screen; // Represents a screen in the application.
import javafx.event.EventHandler; // Interface for handling events in JavaFX.
import javafx.scene.input.MouseEvent; // Represents a mouse event in JavaFX.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class ShowPopup implements EventHandler<MouseEvent>, Runnable {
    private Stage primaryStage; // The primary stage (window) of the application.
    private Screen popUpScreen; // The pop-up screen to show or hide.
    private Screen currentScreen; // The current screen being displayed.
    private boolean visible; // Whether the pop-up screen is visible.
    private boolean pausing; // Whether the current screen should be paused when the pop-up is shown.

    /**
     * Constructs a ShowPopup object with the specified primary stage.
     *
     * @param primaryStage The primary stage (window) of the application.
     */
    public ShowPopup(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.visible = true;
        this.pausing = true;
    }

    /**
     * Sets whether the current screen should be paused when the pop-up is shown.
     *
     * @param pausing Whether to pause the current screen.
     * @return This instance of ShowPopup for method chaining.
     */
    public ShowPopup setPausing(boolean pausing) {
        this.pausing = pausing;
        return this;
    }

    /**
     * Sets the pop-up screen to show or hide.
     *
     * @param popUpScreen The pop-up screen.
     * @return This instance of ShowPopup for method chaining.
     */
    public ShowPopup setPopUpScreen(Screen popUpScreen) {
        this.popUpScreen = popUpScreen;
        return this;
    }

    /**
     * Sets the current screen being displayed.
     *
     * @param currentScreen The current screen.
     * @return This instance of ShowPopup for method chaining.
     */
    public ShowPopup setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
        return this;
    }

    /**
     * Sets whether the pop-up screen should be visible.
     *
     * @param visible Whether the pop-up screen is visible.
     * @return This instance of ShowPopup for method chaining.
     */
    public ShowPopup setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        // Handle mouse click events to show or hide the pop-up screen.
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            popUpScreen.setVisible(visible);
            if (visible) {
                popUpScreen.toFront(); // Bring the pop-up screen to the front.
            }
            if (pausing) currentScreen.pausing(visible); // Pause the current screen if required.
        }
    }

    @Override
    public void run() {
        // Triggered directly (Runnable) to show or hide the pop-up screen.
        if (visible) {
            popUpScreen.toFront(); // Bring the pop-up screen to the front.
        }
        popUpScreen.setVisible(visible); // Set the visibility of the pop-up screen.
    }
}