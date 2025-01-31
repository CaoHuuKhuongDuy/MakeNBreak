/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the pause screen in the application. Displays a semi-transparent overlay
 * and provides options to resume the game or quit to the main menu.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.controllers.mouse.ShowPopup; // Handles the display and visibility of pop-up screens.
import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import com.models.components.CustomButton; // Represents a custom button in the UI.
import javafx.scene.image.ImageView; // Displays an image in the UI.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.
import javafx.scene.shape.Rectangle; // Represents a rectangle shape in JavaFX.

public class PauseScreen extends Screen {

    private ShowPopup showPopup; // Handles the visibility of the pop-up screen.
    private SwitchScreen switchScreen; // Handles screen switching logic.
    private Screen previousScreen; // The screen that was displayed before the pause screen.

    /**
     * Constructs a PauseScreen with the specified primary stage and previous screen.
     *
     * @param primaryStage   The primary stage (window) of the application.
     * @param previousScreen The screen that was displayed before the pause screen.
     */
    public PauseScreen(Stage primaryStage, Screen previousScreen) {
        super(primaryStage);
        this.previousScreen = previousScreen;
        this.initHandlers();
        this.display();
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

        // Create a semi-transparent black overlay.
        Rectangle overlay = new Rectangle(1024, 768);
        overlay.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5)); // Black with 50% opacity.

        // Add the outer panel and background image.
        ImageView imageViewSettingPanel = new ImageView(Globals.getResource("/resources/assets/images/OuterPanel.png"));
        imageViewSettingPanel.setFitWidth(371);
        imageViewSettingPanel.setFitHeight(381);

        ImageView panelBackground = new ImageView(Globals.getResource("/resources/assets/images/meme.png"));
        panelBackground.setLayoutX(253);
        panelBackground.setLayoutY(286);

        // Create the quit button.
        CustomButton quitButton = new CustomButton("QUIT", "/resources/assets/images/Button/redButton.png");
        quitButton.setSize(130, 55);
        quitButton.setOnMouseClicked(this.switchScreen.setScreen(new IntroScreen(primaryStage)));

        // Create the resume button.
        CustomButton resumeButton = new CustomButton("RESUME", "/resources/assets/images/Button/yellowButton.png");
        resumeButton.setOnMouseClicked(this.showPopup.setPopUpScreen(this).setVisible(false));
        resumeButton.setSize(130, 55);

        // Create the cross button.
        CustomButton crossButton = new CustomButton("", "/resources/assets/images/Button/Icon_Cross.png");
        crossButton.setOnMouseClicked(this.showPopup.setPopUpScreen(this).setVisible(false));

        // Position the UI elements.
        imageViewSettingPanel.setLayoutX(314);
        imageViewSettingPanel.setLayoutY(193);

        quitButton.setLayoutX(358);
        quitButton.setLayoutY(474);

        resumeButton.setLayoutX(512);
        resumeButton.setLayoutY(474);

        crossButton.setLayoutX(637);
        crossButton.setLayoutY(220);

        // Add all components to the screen.
        this.getChildren().addAll(overlay, imageViewSettingPanel, panelBackground, quitButton, resumeButton, crossButton);

        // Set the current scene to this screen.
        this.primaryStage.getScene().setRoot(this);
    }
}