package com.screens;

import com.commons.GameType;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.User;
import com.models.components.CustomButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends Screen {

    private SwitchScreen switchScreen;
    private int numberOfPlayers = 0;

    private final List<Button> playerButtons = new ArrayList<>();
    private final List<Button> gameTypeButtons = new ArrayList<>();

    public MainScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        this.getChildren().clear();
        if (Globals.app.getGameType() != null) {
            Globals.app.setGameType(null);
        }
        addTitleImage();
        configureComponents();
        this.primaryStage.getScene().setRoot(this);
    }

    private void addTitleImage() {
        ImageView titleImageView = new ImageView(new Image("/resources/assets/images/title.png"));
        titleImageView.setLayoutX(0);
        titleImageView.setLayoutY(0);
        this.getChildren().add(titleImageView);
    }

    private void configureComponents() {
        Text numOfPlayersText = createLabel("Number of Players", 390, 639);
        TextField playerField = createTextField("Enter number of players", 395, 655);
        Text numOfRoundsText = createLabel("Number of Rounds", 390, 490);
        TextField roundField = createTextField("Enter number of rounds", 395, 505);

        createPlayerButtons(numOfPlayersText, playerField);
        createGameTypeButtons();
        createPlayButton(numOfPlayersText, playerField, roundField);

        numOfPlayersText.setVisible(false);
        playerField.setVisible(false);
    }

    private Text createLabel(String text, int x, int y) {
        Text label = new Text(text);
        label.setStyle("-fx-font-size: 25; -fx-font-family: Poppins; -fx-fill: #FFBF3F;");
        label.setLayoutX(x);
        label.setLayoutY(y);
        this.getChildren().add(label);
        return label;
    }

    private TextField createTextField(String prompt, int x, int y) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-alignment: center; -fx-background-color: white; -fx-opacity: 0.5; -fx-padding: 10px; -fx-font-size: 16px; -fx-font-family: Poppins;");
        field.setLayoutX(x);
        field.setLayoutY(y);
        this.getChildren().add(field);
        return field;
    }

    private void createPlayerButtons(Text numOfPlayersText, TextField playerField) {
        createPlayerButton("2 PLAYER", 2, numOfPlayersText, playerField, 56, 417);
        createPlayerButton("3 PLAYER", 3, numOfPlayersText, playerField, 56, 503);
        createPlayerButton("4 PLAYER", 4, numOfPlayersText, playerField, 56, 592);
        createCustomButton(numOfPlayersText, playerField, 56, 677);
    }

    private void createPlayerButton(String label, int players, Text numOfPlayersText, TextField playerField, int x, int y) {
        CustomButton button = new CustomButton(label, "resources/assets/images/Button/yellowButton.png");
        button.setLayoutX(x);
        button.setLayoutY(y);
        playerButtons.add(button);
        button.setOnMouseClicked(event -> {
            numberOfPlayers = players;
            numOfPlayersText.setVisible(false);
            playerField.setVisible(false);

            updateButtonGroupState(playerButtons, button);
        });
        this.getChildren().add(button);
    }

    private void createCustomButton(Text numOfPlayersText, TextField playerField, int x, int y) {
        CustomButton customButton = new CustomButton("CUSTOMIZE", "resources/assets/images/Button/redButton.png");
        customButton.setLayoutX(x);
        customButton.setLayoutY(y);
        playerButtons.add(customButton);
        customButton.setOnMouseClicked(event -> {
            numOfPlayersText.setVisible(true);
            playerField.setVisible(true);
            updateButtonGroupState(playerButtons, customButton);
        });
        this.getChildren().add(customButton);
    }

    private void createGameTypeButtons() {
        createGameTypeButton("Single Block Type", GameType.SINGLE_BLOCK, "resources/assets/images/Button/longerYellow.png", 715, 417);
        createGameTypeButton("Multiple Block Type", GameType.MULTIPLE_BLOCK, "resources/assets/images/Button/redLonger.png", 702, 503);
    }

    private void createGameTypeButton(String label, GameType type, String imagePath, int x, int y) {
        CustomButton button = new CustomButton(label, imagePath);
        button.setLayoutX(x);
        button.setLayoutY(y);
        gameTypeButtons.add(button);
        button.setOnMouseClicked(event -> {
            Globals.app.setGameType(type);
        updateButtonGroupState(gameTypeButtons, button);
    });
        this.getChildren().add(button);
    }

    private void createPlayButton(Text numOfPlayersText, TextField playerField, TextField roundField) {
        CustomButton playButton = new CustomButton("PLAY", "resources/assets/images/Button/yellowButton.png");
        playButton.setLayoutX(739);
        playButton.setLayoutY(672);
        playButton.setOnMouseClicked(event -> handlePlayButton(numOfPlayersText, playerField, roundField, event));  // Pass event
        this.getChildren().add(playButton);
    }

    private void handlePlayButton(Text numOfPlayersText, TextField playerField, TextField roundField, MouseEvent event) {
        int players = numberOfPlayers;
        int rounds = parseInput(roundField.getText());

        if (playerField.isVisible()) {
            players = parseInput(playerField.getText());
        }

        if (players > 0 && rounds > 0 && Globals.app.getGameType() != null) {
            updateUsers(players);
            switchScreen.setScreen(new LoadingScreen(primaryStage));
            switchScreen.handle(event);
        }
    }

    private int parseInput(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0 ? value : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void updateUsers(int numberOfPlayers) {
        Globals.app.getUsers().clear();
        for (int i = 0; i < numberOfPlayers; i++) {
            Globals.app.addUser(new User("Player " + (i + 1)));
        }
    }

    private void updateButtonGroupState(List<Button> buttons, Button selectedButton) {
        buttons.forEach(button -> button.setStyle("-fx-opacity: 1;")); // Reset style for all buttons
        selectedButton.setStyle("-fx-opacity: 0.3;"); // Apply style for selected button
    }
}