/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: This class represents the main gameplay screen in the game.
 * It manages the game flow, displays game elements (like cards, dice, clock, player score),
 * handles user input (button clicks, dice roll), and manages game events such as 
 * pausing, ending the round, and navigating between different screens.
 *
 * @Hong Minh Dao 
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.screens;

import com.commons.Coordinate; // Coordinate class is used for positioning graphical elements on the screen
import com.commons.Globals; // Globals provides access to application-wide constants and resources
import com.controllers.callback.EndRound; // EndRound handles the logic when a round finishes
import com.controllers.gameplay.PlayGame; // PlayGame handles the gameplay logic and round flow
import com.controllers.commons.SubmitResult; // SubmitResult manages the submission of game results
import com.controllers.mouse.*; // Mouse-related event controllers (e.g., switching screens, showing popups)
import com.models.*; // Models package holds the game logic and data structures (e.g., cards, users)
import com.models.components.CustomButton; // CustomButton is a customized button class used for UI elements
import javafx.application.Platform; // Platform is used to run code on the JavaFX Application thread
import javafx.scene.control.Button; // Button is used to create clickable UI buttons
import javafx.scene.image.Image; // Image is used to load and display images
import javafx.scene.image.ImageView; // ImageView is used to display images on the screen
import javafx.scene.text.Text; // Text is used to display text on the screen
import javafx.stage.Stage; // Stage represents the main window of the application

import java.util.Vector; // Vector is used to store dynamic arrays of cards in the game
import java.util.concurrent.atomic.AtomicBoolean; // AtomicBoolean is used for thread-safe boolean values

public class GameScreen extends Screen {

    private SwitchScreen switchScreen; // Manages the screen transition logic
    private ShowPopup showPopup; // Displays popups on the screen
    private ShowPopup showPopupInstruction; // Displays the instruction popup
    private RollingDice rollingDice; // Handles dice rolling mechanics
    private GenerateCard generateCard; // Generates game cards
    private EndRound endRound; // Ends the round logic
    private SubmitResult submitResult; // Handles result submission at the end of the game
    private PlayGame playGame; // Manages the gameplay logic (e.g., starting a round)
    private SkipCard skipCard; // Handles skipping a card logic

    private PauseScreen pausingPopup; // PauseScreen displays when the game is paused
    private InstructionPopup instructionPopup; // InstructionPopup shows game instructions

    private int userID; // Current user's ID
    private User currentUser; // Current user object

    private CardSet cardSet; // CardSet holds and manages the game cards
    private Clock clock; // Clock displays the timer for the round
    private Dice dice; // Dice is used for rolling and displaying the dice
    private Text userPointText, userIDText, roundText; // UI elements to display points, user ID, and round number

    private AtomicBoolean playing; // Indicates if the game is currently being played

    private BlockContainer blockContainer; // Holds the blocks that the player will use to build their structures
    private int numBlock = 10; // Number of blocks available for the player

    // Constructor to initialize the GameScreen
    public GameScreen(Stage primaryStage) {
        super(primaryStage);
        this.pausingPopup = new PauseScreen(primaryStage, this);
        this.pausingPopup.setVisible(false);
        this.instructionPopup = new InstructionPopup(primaryStage, this);
        this.instructionPopup.setVisible(false);
        this.clock = new Clock(new Coordinate(133, 92));
        this.dice = new Dice(new Coordinate(41, 99), 66, 66, false);
        this.playing = new AtomicBoolean(false);
        cardSet = new CardSet(this, numBlock);
        blockContainer = new BlockContainer(new Coordinate(31, 181), 346, 559);
        this.initHandlers(); // Initialize event handlers
        this.updateUser(0); // Set the default user (userID 0)
    }

    // Updates the current user's information on the screen
    public void updateUser(int userID) {
        this.userID = userID;
        this.submitResult.setUser(userID);
        this.currentUser = Globals.app.getUsers().get(userID);
        this.currentUser.updateUserInforText(); // Update user text info (points, ID, round)
        this.getChildren().remove(userPointText);
        this.getChildren().remove(userIDText);
        this.getChildren().remove(roundText);
        this.getChildren().add(this.currentUser.getUserPointText());
        this.getChildren().add(this.currentUser.getUserIDText());
        this.getChildren().add(this.currentUser.getCurrentRoundText());
        this.userPointText = this.currentUser.getUserPointText();
        this.userIDText = this.currentUser.getUserIDText();
        this.roundText = this.currentUser.getCurrentRoundText();
    }

    // Initializes the game cards by managing their states (opening, closing, skipped)
    public void initCards() {
        Vector <Card> openingCards = cardSet.getOpeningCards();
        Vector <Card> closingCards = cardSet.getClosingCards();
        Vector <Card> skippedCards = cardSet.getSkippedCards();
        Vector <Card> removedCards = cardSet.getRemovedCards();

        // Remove all old cards
        for (Card card : openingCards)
            this.getChildren().remove(card);
        for (Card card : closingCards)
            this.getChildren().remove(card);
        for (Card card : skippedCards)
            this.getChildren().remove(card);
        for (Card card : removedCards)
            this.getChildren().remove(card);

        openingCards.clear();
        closingCards.clear();
        removedCards.clear();
        closingCards.addAll(skippedCards); // Move skipped cards to closing cards
        skippedCards.clear();

        // Add the closing cards back to the screen
        for (Card card : closingCards) {
            this.getChildren().add(card);
        }

        // If no closing cards, generate new ones
        if (closingCards.isEmpty()) {
            cardSet.genNewClosingCard();
        }
    }

    // Initializes all event handlers for buttons and actions on this screen
    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(this);
        this.showPopupInstruction = new ShowPopup(primaryStage).setCurrentScreen(this);
        this.endRound = new EndRound(this);
        this.generateCard = new GenerateCard(this.cardSet, blockContainer).setCallBack(this.endRound);
        this.rollingDice = new RollingDice().setClockCallBack(this.endRound).setGenerateCard(this.generateCard);
        this.submitResult = new SubmitResult(userID, cardSet, generateCard, blockContainer);
        this.playGame = new PlayGame(this);
        this.skipCard = new SkipCard(this.cardSet, this.generateCard, this);
    }

    // Handles end of the game, transitions to the leaderboard screen
    public void handleEndGame() {
        Platform.runLater(() -> {
            if (this.switchScreen == null) {
                System.out.println("SwitchScreen is not initialized!");
                return;
            }
            LeaderboardScreen leaderboardScreen = new LeaderboardScreen(primaryStage);

            this.switchScreen.setScreen(leaderboardScreen);
            this.switchScreen.run();
        });
    }

    // Displays all game elements (cards, buttons, user info, etc.) on the screen
    @Override
    public void display() {
        this.getChildren().clear();
        ImageView title = new ImageView(new Image(Globals.getResource("/resources/assets/images/smallTitle.png")));

        // Back button to return to the main screen
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        backButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));

        // Button to generate a new card
        Button generateCardButton = new Button("Generate Card");
        generateCardButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        generateCardButton.setOnMouseClicked(this.generateCard);

        // Create Clock and Dice
        clock.draw();
        dice.draw();
        dice.setOnMouseClicked(rollingDice.setDependencies(dice, clock));

        // Add Kick Button for skipping cards
        Button kickButton = new Button();
        ImageView imageKickButton = new ImageView(new Image(Globals.getResource("/resources/assets/images/Button/kickButton.png")));
        kickButton.setGraphic(imageKickButton);
        kickButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        kickButton.setOnMouseClicked(this.skipCard);

        // Add other UI components like score rectangle, player icons, and settings buttons
        ImageView scoreRectangle = new ImageView(new Image(Globals.getResource("/resources/assets/images/Rectangle.png")));
        scoreRectangle.setFitWidth(585);
        scoreRectangle.setFitHeight(76);

        ImageView iconCoin = new ImageView(new Image(Globals.getResource("/resources/assets/images/icon_coin.png")));
        iconCoin.setFitWidth(55.35);
        iconCoin.setFitHeight(55.35);

        ImageView iconPlayer = new ImageView(new Image(Globals.getResource("/resources/assets/images/icon_downasaur.png")));
        iconPlayer.setFitWidth(66);
        iconPlayer.setFitHeight(53.8);

        CustomButton iconSettingButton = new CustomButton("", "/resources/assets/images/Icon_Settings.png");
        iconSettingButton.setOnMouseClicked(showPopup.setPopUpScreen(pausingPopup).setVisible(true));

        CustomButton infoButton = new CustomButton("", "/resources/assets/images/Button/info.png");
        infoButton.setOnMouseClicked(showPopupInstruction.setPopUpScreen(instructionPopup).setVisible(true));

        infoButton.setLayoutX(936);
        infoButton.setLayoutY(21);

        title.setLayoutX(10);
        title.setLayoutY(10);

        generateCardButton.setLayoutX(75);
        generateCardButton.setLayoutY(0);

        kickButton.setLayoutX(300);
        kickButton.setLayoutY(101);

        scoreRectangle.setLayoutX(401);
        scoreRectangle.setLayoutY(65);

        iconCoin.setLayoutX(920);
        iconCoin.setLayoutY(75);

        iconPlayer.setLayoutX(413);
        iconPlayer.setLayoutY(77);

        iconSettingButton.setLayoutX(975);
        iconSettingButton.setLayoutY(18);

        // Add closing cards to the screen
        for (int i = cardSet.getClosingCards().size() - 1; i >= 0; i--)
            this.getChildren().add(cardSet.getClosingCards().get(i));

        // Start a round of the game
        this.playGame.playRound();

        // Add all elements to the screen
        this.getChildren().addAll(title, scoreRectangle, clock, dice, kickButton, iconCoin, iconPlayer, iconSettingButton, blockContainer, infoButton);
        this.getChildren().addAll(userPointText, userIDText, roundText);
        this.getChildren().addAll(pausingPopup, instructionPopup);

        this.primaryStage.getScene().setRoot(this);
        this.primaryStage.getScene().setOnKeyPressed(this.submitResult);
    }

    // Pauses the game by setting the clock's state
    public void pausing(boolean pausing) {
        this.clock.setPausing(pausing);
    }

    public Dice getDice() {
        return dice;
    }

    public Clock getClock() {
        return clock;
    }

    public int getUserID() {
        return userID;
    }

    public PlayGame getGamePlay() {
        return playGame;
    }

    public void setPlaying(boolean playing) {
        this.playing.set(playing);
    }

    public BlockContainer getBlockContainer() {
        return blockContainer;
    }

    public boolean getPlaying() {
        return this.playing.get();
    }
}
