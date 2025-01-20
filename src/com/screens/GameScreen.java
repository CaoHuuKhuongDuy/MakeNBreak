package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.controllers.callbacks.EndRound;
import com.controllers.commons.SubmitResult;
import com.controllers.mouse.*;
import com.models.Card;
import com.models.Clock;
import com.models.Dice;
import com.models.User;
import com.models.BlockContainer;
import com.models.components.BuildingBlock;
import com.models.components.ListBuildingBlock;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class GameScreen extends Screen {
    private SwitchScreen switchScreen;
    private ShowPopup showPopup;
    private RollingDice rollingDice;
    private GenerateCard generateCard;
    private EndRound endRound;
    private SubmitResult submitResult;

    private PauseScreen pausingPopup;

    private int userID;
    private User currentUser;


    private Vector<Card> openingCards, closingCards;
    private int numCard = 10;

    private Clock clock;
    private Dice dice;
    private Text userPointText, userIDText;

    private boolean playing;

    private BlockContainer blockContainer;
    private int numBlock = 3;
    private ListBuildingBlock blockGenerator;

    public GameScreen(Stage primaryStage) {
        super(primaryStage);
        this.pausingPopup = new PauseScreen(primaryStage, this);
        this.pausingPopup.setVisible(false);
        this.updateUser(0);

        this.clock = new Clock(new Coordinate(133, 92));
        this.dice = new Dice(new Coordinate(41, 99), 66, 66, false);
        this.playing = false;
        this.blockGenerator = new ListBuildingBlock();
        openingCards = new Vector<>();
        closingCards = new Vector<>();
        blockContainer = new BlockContainer(new Coordinate(31, 181), 346, 559);
        this.initHandlers();
    }

    private void updateUser(int userID) {
        this.userID = userID;
        this.currentUser = Globals.app.getUsers().get(userID);
        this.currentUser.updateUserInforText();
        this.userPointText = this.currentUser.getUserPointText();
        this.userIDText = this.currentUser.getUserIDText();
    }

    private void initCards() {
        for (Card card : closingCards)
            this.getChildren().remove(card);
        for (Card card : openingCards)
            this.getChildren().remove(card);
        openingCards.clear();
        closingCards.clear();
        for (int i = 0; i < numCard; i++) {
            int row = 10;
            int col = 15;
            Vector <BuildingBlock> block = this.blockGenerator.generateRandomBuildingBlocks(numBlock, Globals.app.getGameType());
            this.blockGenerator.setBuildingBlocks(block);
            closingCards.add(new Card(this.blockGenerator, row, col, new Coordinate(700, 155), 261, 174, Globals.app.getGameType(), false));
        }
        for (int i = closingCards.size() - 1; i >= 0; i--)
            this.getChildren().add(closingCards.get(i));
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(this);
        this.endRound = new EndRound(this);
        this.generateCard = new GenerateCard(openingCards, closingCards, blockContainer).setCallBack(this.endRound);
        this.rollingDice = new RollingDice().setClockCallBack(this.endRound);
        this.submitResult = new SubmitResult(userID, openingCards, generateCard, blockContainer);
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

        for (int i = this.closingCards.size() - 1; i >= 0; i--)
            this.getChildren().add(this.closingCards.get(i));

        this.playRound();
        this.getChildren().addAll(scoreRectangle, userPointText, userIDText, backButton, generateCardButton, clock, dice, kickButton,
                iconCoin, iconPlayer, iconSettingButton, blockContainer);
        this.getChildren().addAll(pausingPopup);
//        this.getChildren().add(submitButton);

        this.primaryStage.getScene().setRoot(this);
        this.primaryStage.getScene().setOnKeyPressed(this.submitResult);
    }

    public void pausing(boolean pausing) {
        this.clock.setPausing(pausing);
    }

    public void playRound() {
        this.playing = true;
        this.dice.setInteractable(true);

        this.initCards();
    }

    public void EndRound() {
        this.playing = false;
        this.clock.setRunning(false);
        if (userID == Globals.app.getUsers().size() - 1) {
            userID = 0;
        } else {
            this.updateUser(userID + 1);
        }
        this.playRound();
    }
}