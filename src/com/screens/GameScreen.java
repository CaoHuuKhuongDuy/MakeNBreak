package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.controllers.callback.EndRound;
import com.controllers.gameplay.PlayGame;
import com.controllers.commons.SubmitResult;
import com.controllers.mouse.*;
import com.models.*;
import com.models.components.CustomButton;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameScreen extends Screen {
    private SwitchScreen switchScreen;
    private ShowPopup showPopup;
    private ShowPopup showPopupInstruction;
    private RollingDice rollingDice;
    private GenerateCard generateCard;
    private EndRound endRound;
    private SubmitResult submitResult;
    private PlayGame playGame;
    private SkipCard skipCard;

    private PauseScreen pausingPopup;
    private InstructionPopup instructionPopup;

    private int userID;
    private User currentUser;

    CardSet cardSet;

    private Clock clock;
    private Dice dice;
    private Text userPointText, userIDText, roundText;

    private AtomicBoolean playing;

    private BlockContainer blockContainer;
    private int numBlock = 10;

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
        this.initHandlers();
        this.updateUser(0);
    }

    public void updateUser(int userID) {
        this.userID = userID;
        this.submitResult.setUser(userID);
        this.currentUser = Globals.app.getUsers().get(userID);
        this.currentUser.updateUserInforText();
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

    public void initCards() {
        Vector <Card> openingCards = cardSet.getOpeningCards();
        Vector <Card> closingCards = cardSet.getClosingCards();
        Vector <Card> skippedCards = cardSet.getSkippedCards();
        Vector <Card> removedCards = cardSet.getRemovedCards();
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
        closingCards.addAll(skippedCards);
        skippedCards.clear();
        for (Card card : closingCards) {
            this.getChildren().add(card);
        }
        if (closingCards.isEmpty()) {
            cardSet.genNewClosingCard();
        }
    }

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

    @Override
    public void display() {
        this.getChildren().clear();
        ImageView title = new ImageView(new Image(Globals.getResource("/resources/assets/images/smallTitle.png")));


        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        backButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));

        Button generateCardButton = new Button("Generate Card");
        generateCardButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        generateCardButton.setOnMouseClicked(this.generateCard);

        // Create Clock
        clock.draw();

        // Create Dice
        dice.draw();
        dice.setOnMouseClicked(rollingDice.setDependencies(dice, clock));

        // Create Kick Button
        Button kickButton = new Button();
        ImageView imageKickButton = new ImageView(new Image(Globals.getResource("/resources/assets/images/kickButton.png")));
        kickButton.setGraphic(imageKickButton);
        kickButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        kickButton.setOnMouseClicked(this.skipCard);

        // Add score rectangle
        ImageView scoreRectangle = new ImageView(new Image(Globals.getResource("/resources/assets/images/Rectangle.png")));
        scoreRectangle.setFitWidth(585);
        scoreRectangle.setFitHeight(76);

        // Add coin symbol
        ImageView iconCoin = new ImageView(new Image(Globals.getResource("/resources/assets/images/icon_coin.png")));
        iconCoin.setFitWidth(55.35);
        iconCoin.setFitHeight(55.35);

        // Add player symbol
        ImageView iconPlayer = new ImageView(new Image(Globals.getResource("/resources/assets/images/icon_downasaur.png")));
        iconPlayer.setFitWidth(66);
        iconPlayer.setFitHeight(53.8);

        // Create icon setting button
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

        for (int i = cardSet.getClosingCards().size() - 1; i >= 0; i--)
            this.getChildren().add(cardSet.getClosingCards().get(i));

        this.playGame.playRound();
        this.getChildren().addAll(title, scoreRectangle, clock, dice, kickButton,
                iconCoin, iconPlayer, iconSettingButton, blockContainer, infoButton);
        this.getChildren().addAll(userPointText, userIDText, roundText);
        this.getChildren().addAll(pausingPopup, instructionPopup);


        this.primaryStage.getScene().setRoot(this);
        this.primaryStage.getScene().setOnKeyPressed(this.submitResult);
    }

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

    public User getCurrentUser() {
        return currentUser;
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