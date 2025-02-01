/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the leaderboard screen in the application. Displays the top players
 * based on their scores and provides a button to return to the main menu.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import com.models.User; // Represents a user in the application.
import com.models.components.CustomButton; // Represents a custom button in the UI.
import com.commons.Globals; // Provides global constants and utility methods for the application.
import javafx.scene.control.Label; // Displays a text element on the screen.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

import java.util.Comparator; // Provides functionality for comparing objects.

public class LeaderboardScreen extends Screen {

    private SwitchScreen switchScreen; // Handles screen switching logic.

    public LeaderboardScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
        setupBackground("/resources/assets/images/leaderBoard.png"); // Set up the background image.
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
        this.getStylesheets().add(Globals.getResource("/resources/assets/styles/Text.css")); // Add custom styles.

        // Load trophy images for the leaderboard.
        Image firstPlace = new Image(Globals.getResource("/resources/assets/images/Trophy.png"));
        ImageView firstPlaceView = new ImageView(firstPlace);

        Image secondPlace = new Image(Globals.getResource("/resources/assets/images/Gold.png"));
        ImageView secondPlaceView = new ImageView(secondPlace);

        Image thirdPlace = new Image(Globals.getResource("/resources/assets/images/Silver.png"));
        ImageView thirdPlaceView = new ImageView(thirdPlace);

        Image fourthPlace = new Image(Globals.getResource("/resources/assets/images/Bronze.png"));
        ImageView fourthPlaceView = new ImageView(fourthPlace);

        // Sort users by their scores in descending order.
        Globals.app.getUsers().sort(Comparator.comparingInt(User::getPoint).reversed());

        int numberOfUsers = Globals.app.getUsers().size();

        // Display the top players based on the number of users.
        if (numberOfUsers >= 4) {
            addLeaderboardEntry(firstPlaceView, Globals.app.getUsers().get(0), 474, 248);
            addLeaderboardEntry(secondPlaceView, Globals.app.getUsers().get(1), 142, 442);
            addLeaderboardEntry(thirdPlaceView, Globals.app.getUsers().get(2), 460, 442);
            addLeaderboardEntry(fourthPlaceView, Globals.app.getUsers().get(3), 767, 442);
        } else if (numberOfUsers == 3) {
            addLeaderboardEntry(secondPlaceView, Globals.app.getUsers().get(0), 461, 249);
            addLeaderboardEntry(thirdPlaceView, Globals.app.getUsers().get(1), 191, 429);
            addLeaderboardEntry(fourthPlaceView, Globals.app.getUsers().get(2), 728, 429);
        } else if (numberOfUsers == 2) {
            addLeaderboardEntry(secondPlaceView, Globals.app.getUsers().get(0), 265, 337);
            addLeaderboardEntry(thirdPlaceView, Globals.app.getUsers().get(1), 656, 328);
        } else if (numberOfUsers == 1) {
            addLeaderboardEntry(firstPlaceView, Globals.app.getUsers().getFirst(), 474, 330);
        }

        // Create and configure the home button.
        CustomButton homeButton = new CustomButton("", "/resources/assets/images/Button/home.png");
        homeButton.setOnMouseClicked(this.switchScreen.setScreen(new IntroScreen(primaryStage)));

        homeButton.setLayoutX(485);
        homeButton.setLayoutY(664);
        this.getChildren().add(homeButton);

        // Set the current scene to this screen.
        this.primaryStage.getScene().setRoot(this);
    }

    /**
     * Adds a leaderboard entry with the specified trophy image, user, and position.
     *
     * @param placeView The trophy image for the leaderboard position.
     * @param user      The user to display.
     * @param x         The x-coordinate of the entry.
     * @param y         The y-coordinate of the entry.
     */
    private void addLeaderboardEntry(ImageView placeView, User user, double x, double y) {
        placeView.setLayoutX(x);
        placeView.setLayoutY(y);

        // Create and configure the user ID label.
        Label userIdLabel = new Label("Player: " + user.getUserID());
        userIdLabel.getStyleClass().add("label-user-id");

        // Create and configure the score label.
        Label scoreLabel = new Label("Score: " + user.getPoint());
        scoreLabel.getStyleClass().add("label-score");

        // Position the labels.
        userIdLabel.setLayoutX(x);
        userIdLabel.setLayoutY(y + placeView.getImage().getHeight() + 10);

        scoreLabel.setLayoutX(x);
        scoreLabel.setLayoutY(userIdLabel.getLayoutY() + 30);

        // Add the trophy image and labels to the screen.
        this.getChildren().addAll(placeView, userIdLabel, scoreLabel);
    }
}