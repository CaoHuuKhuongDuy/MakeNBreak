/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: This class serves as the entry point for the application. It initializes the primary stage,
 * sets up the initial screen (IntroScreen), and configures the application window.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.screens.IntroScreen; // Represents the introductory screen of the application.
import com.screens.Screen; // Abstract base class for all screens in the application.
import javafx.application.Application; // Base class for JavaFX applications.
import javafx.scene.Scene; // Represents the container for all content in a JavaFX application window.
import javafx.scene.control.Label; // Displays a text element on the screen.
import javafx.scene.layout.VBox; // A layout container that arranges its children in a vertical column.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a temporary label and layout to display while the application initializes.
        Label dummyLabel = new Label("Loading...");
        VBox dummyLayout = new VBox(10, dummyLabel);
        Scene dummyScene = new Scene(dummyLayout);

        // Set the initial scene to the primary stage.
        primaryStage.setScene(dummyScene);

        // Initialize global constants and set the default resolution.
        Globals.init();
        Globals.setDefaultResolution(1024, 768);

        // Create and display the introductory screen.
        Screen mainScreen = new IntroScreen(primaryStage);
        mainScreen.display();

        // Configure the primary stage (window) settings.
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setTitle("Make N Break");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application.
        launch(args);
    }
}