package com.screens;

import com.commons.GameType;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.User;
import com.models.components.CustomButton;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class MainScreen extends Screen {

    private SwitchScreen switchScreen;
    private int numberOfPlayer = 0; // Moved to class-level

    public MainScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
        Globals.app.setGameType(null);
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        this.getChildren().clear();

        Image titleImage = new Image("/resources/assets/images/title.png");
        ImageView titleImageView = new ImageView(titleImage);

        Text numOfPlayers = new Text("Number of Players");
        numOfPlayers.setStyle("-fx-font-size: 25; -fx-font-family: Poppins; -fx-fill: #FFBF3F;");

        TextField playerField = new TextField();
        playerField.setPromptText("Enter number of players");
        playerField.setStyle("-fx-alignment: center; -fx-background-color: white; -fx-opacity: 0.5; -fx-padding: 10px; -fx-font-size: 16px; -fx-font-family: Poppins; ");

        Text numOfRounds = new Text("Number of Rounds");
        numOfRounds.setStyle("-fx-font-size: 25; -fx-font-family: Poppins; -fx-fill: #FFBF3F;");

        TextField roundField = new TextField();
        roundField.setPromptText("Enter number of rounds");
        roundField.setStyle("-fx-alignment: center; -fx-background-color: white; -fx-opacity: 0.5; -fx-padding: 10px; -fx-font-size: 16px; -fx-font-family: Poppins; ");

        // Player Selection Buttons
        CustomButton twoPlayerButton = new CustomButton("2 PLAYER", "resources/assets/images/Button/yellowButton.png");
        twoPlayerButton.setOnMouseClicked(event -> {
            numberOfPlayer = 2; // Set player count without calling updateUsers()
            numOfPlayers.setVisible(false);
            playerField.setVisible(false);
        });

        CustomButton threePlayerButton = new CustomButton("3 PLAYER", "resources/assets/images/Button/yellowButton.png");
        threePlayerButton.setOnMouseClicked(event -> {
            numberOfPlayer = 3;
            numOfPlayers.setVisible(false);
            playerField.setVisible(false);
        });

        CustomButton fourPlayerButton = new CustomButton("4 PLAYER", "resources/assets/images/Button/yellowButton.png");
        fourPlayerButton.setOnMouseClicked(event -> {
            numberOfPlayer = 4;
            numOfPlayers.setVisible(false);
            playerField.setVisible(false);
        });

        CustomButton custom = new CustomButton("CUSTOMIZE", "resources/assets/images/Button/redButton.png");
        custom.setOnMouseClicked(event -> {
            numOfPlayers.setVisible(true);
            playerField.setVisible(true);
        });

        // Play Button
        CustomButton playButton = new CustomButton("PLAY", "resources/assets/images/Button/yellowButton.png");
        playButton.setOnMouseClicked(event -> {
            boolean validInput = true; // Flag for validation

            int rounds = 0; // Variable to store number of rounds
            int players = numberOfPlayer; // Default player count from selection

            // Validate rounds
            if (roundField.getText().isEmpty()) {
                validInput = false;
            } else {
                try {
                    rounds = Integer.parseInt(roundField.getText());
                    if (rounds <= 0) { // Check if rounds > 0
                        validInput = false;
                    } else {
                        Globals.app.setNumberOfRound(rounds); // Update rounds
                    }
                } catch (NumberFormatException e) {
                    validInput = false;
                }
            }

            // Validate players (if custom field is visible)
            if (playerField.isVisible() && !playerField.getText().isEmpty()) {
                try {
                    players = Integer.parseInt(playerField.getText()); // Use custom input
                    if (players <= 0) { // Check if players > 0
                        validInput = false;
                    }
                } catch (NumberFormatException e) {
                    validInput = false;
                }
            }

            // Update players based on the final number of players
            if (validInput) {
                updateUsers(players); // Call updateUsers only if valid input
            }

            // Validate game type
            if (Globals.app.getGameType() == null) {
                validInput = false;
            }

            // Proceed only if all inputs are valid
            if (validInput) {
                switchScreen.setScreen(new GameScreen(primaryStage)); // Switch screen
                switchScreen.handle(event);
            }
        });

        // Game Type Buttons
        CustomButton singleButton = new CustomButton("Single Block Type", "resources/assets/images/Button/longerYellow.png");
        singleButton.setOnMouseClicked(event -> Globals.app.setGameType(GameType.SINGLE_BLOCK));

        CustomButton multiButton = new CustomButton("Multiple Block Type", "resources/assets/images/Button/redLonger.png");
        multiButton.setOnMouseClicked(event -> Globals.app.setGameType(GameType.MULTIPLE_BLOCK));

        // Layout Configurations
        titleImageView.setLayoutX(0);
        titleImageView.setLayoutY(0);
        twoPlayerButton.setLayoutX(56);
        twoPlayerButton.setLayoutY(417);
        threePlayerButton.setLayoutX(56);
        threePlayerButton.setLayoutY(503);
        fourPlayerButton.setLayoutX(56);
        fourPlayerButton.setLayoutY(592);
        custom.setLayoutX(56);
        custom.setLayoutY(677);
        singleButton.setLayoutX(715);
        singleButton.setLayoutY(417);
        numOfPlayers.setLayoutX(390);
        numOfPlayers.setLayoutY(639);
        playerField.setLayoutX(395);
        playerField.setLayoutY(655);
        multiButton.setLayoutX(702);
        multiButton.setLayoutY(503);
        numOfRounds.setLayoutX(390);
        numOfRounds.setLayoutY(490);
        roundField.setLayoutX(395);
        roundField.setLayoutY(505);
        playButton.setLayoutX(739);
        playButton.setLayoutY(672);

        numOfPlayers.setVisible(false);
        playerField.setVisible(false);

        this.getChildren().addAll(twoPlayerButton, threePlayerButton, fourPlayerButton, custom,
                numOfRounds, roundField,
                singleButton, multiButton,
                playButton, titleImageView,
                numOfPlayers, playerField);

        this.primaryStage.getScene().setRoot(this);
    }

    private void updateUsers(int numberOfPlayers) {
        Globals.app.getUsers().clear();
        for (int i = 0; i < numberOfPlayers; i++) {
            Globals.app.addUser(new User("Player " + (i + 1)));
        }
    }
}