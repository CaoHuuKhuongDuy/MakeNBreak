/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents the main screen of the application. This screen allows users to configure
 * the game parameter, such as the number of players, number of rounds, and game type (single-block
 * or multiple-block). It also handles navigation to the loading screen once the parameter are confirmed.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.screens;

import com.commons.GameType; // Represents the type of game (single-block or multiple-block).
import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.controllers.mouse.SwitchScreen; // Handles screen switching logic.
import com.models.User; // Represents a user in the application.
import com.models.components.CustomButton; // Represents a custom button in the UI.
import javafx.scene.control.Button; // Represents a button in JavaFX.
import javafx.scene.control.TextField; // Represents a text input field in JavaFX.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.
import javafx.scene.input.MouseEvent; // Represents a mouse event in JavaFX.
import javafx.scene.text.Text; // Represents a text node in JavaFX.
import javafx.stage.Stage; // Represents the main window of the JavaFX application.

import java.util.ArrayList; // A dynamic array implementation.
import java.util.List; // Represents a collection of elements.

public class MainScreen extends Screen {

    private SwitchScreen switchScreen; // Handles screen switching logic.
    private int numberOfPlayers = 0; // The number of players selected by the user.

    private final List<Button> playerButtons = new ArrayList<>(); // Stores buttons for selecting the number of players.
    private final List<Button> gameTypeButtons = new ArrayList<>(); // Stores buttons for selecting the game type.

    /**
     * Constructs a MainScreen with the specified primary stage.
     *
     * @param primaryStage The primary stage (window) of the application.
     */
    public MainScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {
        // Initialize the screen switching logic.
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        // Clear the screen and reset game type if necessary.
        this.getChildren().clear();
        if (Globals.app.getGameType() != null) {
            Globals.app.setGameType(null);
        }

        // Add the title image and configure UI components.
        addTitleImage();
        configureComponents();
        this.primaryStage.getScene().setRoot(this);
    }

    /**
     * Adds the title image to the screen.
     */
    private void addTitleImage() {
        ImageView titleImageView = new ImageView(new Image(Globals.getResource("/resources/assets/images/title.png")));
        titleImageView.setLayoutX(0);
        titleImageView.setLayoutY(0);
        this.getChildren().add(titleImageView);
    }

    /**
     * Configures the UI components, including labels, text fields, and buttons.
     */
    private void configureComponents() {
        Text numOfPlayersText = createLabel("Number of Players", 390, 639);
        TextField playerField = createTextField("Enter number of players", 395, 655);
        Text numOfRoundsText = createLabel("Number of Rounds", 390, 490);
        TextField roundField = createTextField("Enter number of rounds", 395, 505);

        // Create buttons for selecting the number of players and game type.
        createPlayerButtons(numOfPlayersText, playerField);
        createGameTypeButtons();
        createPlayButton(numOfPlayersText, playerField, roundField);

        // Hide the number of players text and field initially.
        numOfPlayersText.setVisible(false);
        playerField.setVisible(false);
    }

    /**
     * Creates a text label with the specified text and position.
     *
     * @param text The text to display.
     * @param x    The x-coordinate of the label.
     * @param y    The y-coordinate of the label.
     * @return The created Text object.
     */
    private Text createLabel(String text, int x, int y) {
        Text label = new Text(text);
        label.setStyle("-fx-font-size: 25; -fx-font-family: Poppins; -fx-fill: #FFBF3F;");
        label.setLayoutX(x);
        label.setLayoutY(y);
        this.getChildren().add(label);
        return label;
    }

    /**
     * Creates a text input field with the specified prompt and position.
     *
     * @param prompt The prompt text to display in the field.
     * @param x      The x-coordinate of the field.
     * @param y      The y-coordinate of the field.
     * @return The created TextField object.
     */
    private TextField createTextField(String prompt, int x, int y) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-alignment: center; -fx-background-color: white; -fx-opacity: 0.5; -fx-padding: 10px; -fx-font-size: 16px; -fx-font-family: Poppins;");
        field.setLayoutX(x);
        field.setLayoutY(y);
        this.getChildren().add(field);
        return field;
    }

    /**
     * Creates buttons for selecting the number of players.
     *
     * @param numOfPlayersText The text label for the number of players.
     * @param playerField      The text field for customizing the number of players.
     */
    private void createPlayerButtons(Text numOfPlayersText, TextField playerField) {
        createPlayerButton("2 PLAYER", 2, numOfPlayersText, playerField, 56, 417);
        createPlayerButton("3 PLAYER", 3, numOfPlayersText, playerField, 56, 503);
        createPlayerButton("4 PLAYER", 4, numOfPlayersText, playerField, 56, 592);
        createCustomButton(numOfPlayersText, playerField, 56, 677);
    }

    /**
     * Creates a button for selecting a specific number of players.
     *
     * @param label            The label for the button.
     * @param players          The number of players associated with the button.
     * @param numOfPlayersText The text label for the number of players.
     * @param playerField      The text field for customizing the number of players.
     * @param x                The x-coordinate of the button.
     * @param y                The y-coordinate of the button.
     */
    private void createPlayerButton(String label, int players, Text numOfPlayersText, TextField playerField, int x, int y) {
        CustomButton button = new CustomButton(label, "/resources/assets/images/Button/yellowButton.png");
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

    /**
     * Creates a button for customizing the number of players.
     *
     * @param numOfPlayersText The text label for the number of players.
     * @param playerField      The text field for customizing the number of players.
     * @param x                The x-coordinate of the button.
     * @param y                The y-coordinate of the button.
     */
    private void createCustomButton(Text numOfPlayersText, TextField playerField, int x, int y) {
        CustomButton customButton = new CustomButton("CUSTOMIZE", "/resources/assets/images/Button/redButton.png");
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

    /**
     * Creates buttons for selecting the game type (single-block or multiple-block).
     */
    private void createGameTypeButtons() {
        createGameTypeButton("Single Block Type", GameType.SINGLE_BLOCK, "/resources/assets/images/Button/longerYellow.png", 715, 417);
        createGameTypeButton("Multiple Block Type", GameType.MULTIPLE_BLOCK, "/resources/assets/images/Button/redLonger.png", 702, 503);
    }

    /**
     * Creates a button for selecting a specific game type.
     *
     * @param label     The label for the button.
     * @param type      The game type associated with the button.
     * @param imagePath The path to the button's background image.
     * @param x         The x-coordinate of the button.
     * @param y         The y-coordinate of the button.
     */
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

    /**
     * Creates the "PLAY" button and sets its action.
     *
     * @param numOfPlayersText The text label for the number of players.
     * @param playerField      The text field for customizing the number of players.
     * @param roundField       The text field for entering the number of rounds.
     */
    private void createPlayButton(Text numOfPlayersText, TextField playerField, TextField roundField) {
        CustomButton playButton = new CustomButton("PLAY", "/resources/assets/images/Button/yellowButton.png");
        playButton.setLayoutX(739);
        playButton.setLayoutY(672);
        playButton.setOnMouseClicked(event -> handlePlayButton(numOfPlayersText, playerField, roundField, event));
        this.getChildren().add(playButton);
    }

    /**
     * Handles the action when the "PLAY" button is clicked.
     *
     * @param numOfPlayersText The text label for the number of players.
     * @param playerField      The text field for customizing the number of players.
     * @param roundField       The text field for entering the number of rounds.
     * @param event            The mouse event that triggered the action.
     */
    private void handlePlayButton(Text numOfPlayersText, TextField playerField, TextField roundField, MouseEvent event) {
        int players = numberOfPlayers;
        int rounds = parseInput(roundField.getText());

        if (playerField.isVisible()) {
            players = parseInput(playerField.getText());
        }

        if (players > 0 && rounds > 0 && Globals.app.getGameType() != null) {
            updateUsers(players);
            Globals.app.setNumberOfRound(rounds);
            switchScreen.setScreen(new LoadingScreen(primaryStage));
            switchScreen.handle(event);
        }
    }

    /**
     * Parses the input from a text field and returns an integer value.
     *
     * @param input The input string to parse.
     * @return The parsed integer value, or -1 if the input is invalid.
     */
    private int parseInput(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0 ? value : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Updates the list of users based on the selected number of players.
     *
     * @param numberOfPlayers The number of players to create.
     */
    private void updateUsers(int numberOfPlayers) {
        Globals.app.getUsers().clear();
        for (int i = 1; i <= numberOfPlayers; i++) {
            User user = new User("Player " + (i));
            user.setUserID(i);
            Globals.app.addUser(user);
        }
    }

    /**
     * Updates the visual state of a button group to indicate the selected button.
     *
     * @param buttons        The list of buttons in the group.
     * @param selectedButton The button that was selected.
     */
    private void updateButtonGroupState(List<Button> buttons, Button selectedButton) {
        buttons.forEach(button -> button.setStyle("-fx-opacity: 1;")); // Reset style for all buttons
        selectedButton.setStyle("-fx-opacity: 0.3;"); // Apply style for selected button
    }
}