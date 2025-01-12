package com.screens;

import com.commons.Coordinate;
import com.commons.GameType;
import com.commons.Globals;
import com.controllers.mouse.GenerateCard;
import com.controllers.mouse.RollingDice;
import com.controllers.mouse.ShowScreen;
import com.controllers.mouse.SwitchScreen;
import com.models.Card;
import com.models.Clock;
import com.models.Dice;
import com.models.User;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Vector;


public class GameScreen extends Screen {
    private SwitchScreen switchScreen;
    private ShowScreen showScreen;
    private RollingDice rollingDice;
    private GenerateCard generateCard;

    private PauseScreen pausingPopup;

    private int userID;

    private Vector<Card> openingCards, closingCards;
    private int numCard = 10;

    public GameScreen(Stage primaryStage) {
        super(primaryStage);
        this.pausingPopup = new PauseScreen(primaryStage);
        this.pausingPopup.setVisible(false);
        this.initCards();
        this.initHandlers();
        this.userID = 0;
    }

    private void initCards() {
        openingCards = new Vector<>();
        closingCards = new Vector<>();
        for (int i = 0; i < numCard; i++)
            closingCards.add(new Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, GameType.MULTIPLE_BLOCK), new Coordinate(700, 155), 261, 174, GameType.SINGLE_BLOCK, false));
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
        this.showScreen = new ShowScreen(primaryStage);
        this.rollingDice = new RollingDice();
        this.generateCard = new GenerateCard(openingCards, closingCards);
    }

    @Override
    public void display() {
        this.getChildren().clear();

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        backButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));


//        Card card1 = new  Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, GameType.MULTIPLE_BLOCK), new Coordinate(420, 155), 261, 174, GameType.MULTIPLE_BLOCK);
//        Card card2 = new  Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, GameType.SINGLE_BLOCK), new Coordinate(700, 155), 261, 174, GameType.SINGLE_BLOCK, false);
//        card1.draw();
//        card2.draw();
        for (int i = 0; i < numCard; i++) {
            closingCards.get(i).draw();
        }

        Button generateCardButton = new Button("Generate Card");
        generateCardButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        generateCardButton.setOnMouseClicked(this.generateCard);

        // Create Clock
        Clock clock = new Clock(new Coordinate(133, 92));
        clock.draw();

        // Create Dice
        Dice dice = new Dice(new Coordinate(41, 99), 66, 66);
        dice.draw();
        dice.setOnMouseClicked(rollingDice.setDependencies(dice, clock));

        // Create Kick Button
        Button kickButton = new Button();
        ImageView imageKickButton = new ImageView(new Image("/resources/assets/images/kickButton.png"));
        kickButton.setGraphic(imageKickButton);
        kickButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        // Add play board
        ImageView playBoard = new ImageView(new Image("/resources/assets/images/board.png"));
        playBoard.setFitWidth(600);
        playBoard.setFitHeight(400);

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



        // Add frame containing blocks
        ImageView blockRectangle = new ImageView(new Image("/resources/assets/images/blockRectangle.png"));
        blockRectangle.setFitWidth(346);
        blockRectangle.setFitHeight(559);

        // Create icon setting button
        Button iconSettingButton = new Button();
        ImageView imageIconSettingButton = new ImageView(new Image("/resources/assets/images/Icon_Settings.png"));
        imageIconSettingButton.setFitWidth(30);
        imageIconSettingButton.setFitHeight(30);
        iconSettingButton.setGraphic(imageIconSettingButton);
        iconSettingButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        iconSettingButton.setOnMouseClicked(showScreen.setScreen(pausingPopup).setVisible(true));

        generateCardButton.setLayoutX(75);
        generateCardButton.setLayoutY(0);

        kickButton.setLayoutX(300);
        kickButton.setLayoutY(101);

        playBoard.setLayoutX(395);
        playBoard.setLayoutY(340);

        scoreRectangle.setLayoutX(401);
        scoreRectangle.setLayoutY(65);

        iconCoin.setLayoutX(920);
        iconCoin.setLayoutY(75);

        iconPlayer.setLayoutX(413);
        iconPlayer.setLayoutY(77);

        blockRectangle.setLayoutX(31);
        blockRectangle.setLayoutY(181);

        iconSettingButton.setLayoutX(975);
        iconSettingButton.setLayoutY(18);

        for (int i = this.closingCards.size() - 1; i >= 0; i--)
            this.getChildren().add(this.closingCards.get(i));


        this.getChildren().addAll(backButton, generateCardButton, clock, dice, kickButton, playBoard,
                scoreRectangle, iconCoin, iconPlayer, blockRectangle, iconSettingButton);

        // Add all the popups
        this.getChildren().addAll(pausingPopup);


        this.primaryStage.getScene().setRoot(this);
    }

    private void regenerateCard(Card... cards) {
        for (Card card : cards) {
            card.regenerate();
            System.out.println("Card regenerated");
        }

    }
}