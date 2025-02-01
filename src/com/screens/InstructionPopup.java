/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the instruction pop-up screen in the application. Displays game
 * instructions and provides a button to return to the previous screen.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.controllers.mouse.ShowPopup; // Handles the display and visibility of pop-up screens.
import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import com.models.components.CustomButton; // Represents a custom button in the UI.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class InstructionPopup extends Screen {

    private ShowPopup showPopup; // Handles the visibility of the pop-up screen.
    private SwitchScreen switchScreen; // Handles screen switching logic.
    private Screen previousScreen; // The screen that was displayed before the pop-up.

    public InstructionPopup(Stage primaryStage, Screen previousScreen) {
        super(primaryStage);
        this.previousScreen = previousScreen;
        this.initHandlers();
        this.display();
        setupBackground("/resources/assets/images/instructions.png"); // Set up the background image.
    }

    @Override
    public void initHandlers() {
        // Initialize the pop-up and screen switching logic.
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(previousScreen);
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        // Clear the screen and set up the UI elements.
        this.getChildren().clear();

        // Create and configure the return button.
        CustomButton returnButton = new CustomButton("", "/resources/assets/images/Button/Icon_Cross.png");
        returnButton.setOnMouseClicked(this.showPopup.setPopUpScreen(this).setVisible(false));

        returnButton.setLayoutX(973);
        returnButton.setLayoutY(20);

        // Add the return button to the screen.
        this.getChildren().addAll(returnButton);

        // Set the current scene to this screen.
        this.primaryStage.getScene().setRoot(this);
    }
}