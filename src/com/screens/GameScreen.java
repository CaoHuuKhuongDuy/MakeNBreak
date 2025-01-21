package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.controllers.callback.EndRound;
import com.controllers.gameplay.PlayGame;
import com.controllers.commons.SubmitResult;
import com.controllers.mouse.*;
import com.models.*;
import com.models.components.BuildingBlock;
import com.models.components.ListBuildingBlock;
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
    private RollingDice rollingDice;
    private GenerateCard generateCard;
    private EndRound endRound;
    private SubmitResult submitResult;
    private PlayGame playGame;
    private SkipCard skipCard;

    private PauseScreen pausingPopup;

    private int userID;
    private User currentUser;

    CardSet cardSet;
    private int numCard = 15;

    private Clock clock;
    private Dice dice;
    private Text userPointText, userIDText;

    private AtomicBoolean playing;

    private BlockContainer blockContainer;
    private int numBlock = 3;
    private ListBuildingBlock blockGenerator;

    public GameScreen(Stage primaryStage) {
        super(primaryStage);
        this.pausingPopup = new PauseScreen(primaryStage, this);
        this.pausingPopup.setVisible(false);

        this.clock = new Clock(new Coordinate(133, 92));
        this.dice = new Dice(new Coordinate(41, 99), 66, 66, false);
        this.playing = new AtomicBoolean(false);
        this.blockGenerator = new ListBuildingBlock();
        cardSet = new CardSet();
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
        this.getChildren().add(this.currentUser.getUserPointText());
        this.getChildren().add(this.currentUser.getUserIDText());
        this.userPointText = this.currentUser.getUserPointText();
        this.userIDText = this.currentUser.getUserIDText();
    }

    public void initCards() {
        Vector <Card> openingCards = cardSet.getOpeningCards();
        Vector <Card> closingCards = cardSet.getClosingCards();
        Vector <Card> skippedCards = cardSet.getSkippedCards();
        for (Card card : openingCards)
            this.getChildren().remove(card);
        for (Card card : closingCards)
            this.getChildren().remove(card);
        for (Card card : skippedCards)
            this.getChildren().remove(card);
        openingCards.clear();
        closingCards.clear();
        closingCards.addAll(skippedCards);
        skippedCards.clear();
        for (int i = 0; i < numCard; i++) {
            int row = 10;
            int col = 15;
            Vector <BuildingBlock> block = this.blockGenerator.generateRandomBuildingBlocks(numBlock, Globals.app.getGameType());
            this.blockGenerator.setBuildingBlocks(block);
            closingCards.add(new Card(this.blockGenerator, row, col, new Coordinate(700, 155), 261, 174, Globals.app.getGameType(), false));
        }
        for (int i = closingCards.size() - 1; i >= 0; i--) {
            this.getChildren().add(closingCards.get(i));
        }
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(this);
        this.endRound = new EndRound(this);
        this.generateCard = new GenerateCard(this.cardSet, blockContainer).setCallBack(this.endRound);
        this.rollingDice = new RollingDice().setClockCallBack(this.endRound).setGenerateCard(this.generateCard);
        this.submitResult = new SubmitResult(userID, cardSet, generateCard, blockContainer);
        this.playGame = new PlayGame(this);
        this.skipCard = new SkipCard(this.cardSet, this.generateCard);
    }

    @Override
    public void display() {
        this.getChildren().clear();


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
        ImageView imageKickButton = new ImageView(new Image("/resources/assets/images/kickButton.png"));
        kickButton.setGraphic(imageKickButton);
        kickButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        kickButton.setOnMouseClicked(this.skipCard);

        // Add score rectangle
        ImageView scoreRectangle = new ImageView(new Image("/resources/assets/images/Rectangle.png"));
        scoreRectangle.setFitWidth(585);
        scoreRectangle.setFitHeight(76);

        // Add coin symbol
        ImageView iconCoin = new ImageView(new Image("/resources/assets/images/icon_coin.png"));
        iconCoin.setFitWidth(55.35);
        iconCoin.setFitHeight(55.35);

        // Add player symbol
        ImageView iconPlayer = new ImageView(new Image("/resources/assets/images/icon_downasaur.png"));
        iconPlayer.setFitWidth(66);
        iconPlayer.setFitHeight(53.8);

        // Create icon setting button
        Button iconSettingButton = new Button();
        ImageView imageIconSettingButton = new ImageView(new Image("/resources/assets/images/Icon_Settings.png"));
        imageIconSettingButton.setFitWidth(30);
        imageIconSettingButton.setFitHeight(30);
        iconSettingButton.setGraphic(imageIconSettingButton);
        iconSettingButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        iconSettingButton.setOnMouseClicked(showPopup.setPopUpScreen(pausingPopup).setVisible(true));

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
        this.getChildren().addAll(scoreRectangle, backButton, generateCardButton, clock, dice, kickButton,
                iconCoin, iconPlayer, iconSettingButton, blockContainer);
        this.getChildren().addAll(pausingPopup);
        this.getChildren().addAll(userPointText, userIDText);

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