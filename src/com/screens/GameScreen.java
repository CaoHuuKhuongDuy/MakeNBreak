package com.screens;

import com.commons.Coordinate;
import com.commons.GameType;
import com.commons.Globals;
import com.controllers.callbacks.EndRound;
import com.controllers.mouse.GenerateCard;
import com.controllers.mouse.RollingDice;
import com.controllers.mouse.ShowPopup;
import com.controllers.mouse.SwitchScreen;
import com.models.Card;
import com.models.Clock;
import com.models.Dice;
import com.models.User;
import com.models.components.BlockContainer;
import com.models.components.BuildingBlock;
import com.models.components.ListBuildingBlock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class GameScreen extends Screen {
    private SwitchScreen switchScreen;
    private ShowPopup showPopup;
    private RollingDice rollingDice;
    private GenerateCard generateCard;
    private EndRound endRound;

    private PauseScreen pausingPopup;

    private int userID;
    private Text userPointText, userIDText;

    private Vector<Card> openingCards, closingCards;
    private int numCard = 30;

    private Clock clock;
    private Dice dice;

    private boolean playing;

    private BlockContainer blockContainer;
    private int numBlock = 30;
    private ListBuildingBlock blockGenerator;

    public GameScreen(Stage primaryStage) {
        super(primaryStage);
        this.pausingPopup = new PauseScreen(primaryStage, this);
        this.pausingPopup.setVisible(false);
        this.userID = 0;
        Font jerseyFont = Font.loadFont(getClass().getResourceAsStream("/resources/assets/fonts/Jersey25.ttf"), 60);
        this.userPointText = new Text();
        this.userIDText = new Text();
        this.userPointText.setFont(jerseyFont);
        this.userIDText.setFont(jerseyFont);
        this.clock = new Clock(new Coordinate(133, 92));
        this.dice = new Dice(new Coordinate(41, 99), 66, 66, false);
        this.playing = false;
        this.blockGenerator = new ListBuildingBlock();
        this.initHandlers();
        this.initCards();
    }

    private void initCards() {
        openingCards = new Vector<>();
        closingCards = new Vector<>();
        for (int i = 0; i < numCard; i++)
            closingCards.add(new Card(this.blockGenerator.generateBuilding(10, 15, 10, Globals.app.getGameType()), new Coordinate(700, 155), 261, 174, Globals.app.getGameType(), false));
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(this);
        this.endRound = new EndRound(this);
        this.generateCard = new GenerateCard(openingCards, closingCards).setCallBack(this.endRound);
        this.rollingDice = new RollingDice().setClockCallBack(this.endRound);
    }

    @Override
    public void display() {
        this.getChildren().clear();

        blockContainer = new BlockContainer(new Coordinate(31, 181), 346, 559);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        backButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));

        for (int i = 0; i < numCard; i++) {
            closingCards.get(i).draw();
        }

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

        this.updateUserInforText();

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

        playBoard.setLayoutX(395);
        playBoard.setLayoutY(340);

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
        this.getChildren().addAll(scoreRectangle, userPointText, userIDText, backButton, generateCardButton, clock, dice, kickButton, playBoard,
                iconCoin, iconPlayer, iconSettingButton, blockContainer);
        this.getChildren().addAll(pausingPopup);

//        int blockSpacing = 20; // Spacing between blocks
//        int blocksPerRow = 3;
//        int topGap = 10; // Gap above the first line of blocks
//        int bottomGap = 10;
//        int width = 346;
//        int height = 559;
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(blockSpacing);
//        gridPane.setVgap(blockSpacing);
//        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0));
////        Vector <BuildingBlock> blocks = this.blockGenerator.generateRandomBuildingBlocks(numBlock);
//        Vector <BuildingBlock> blocks = new Vector<>(new Vector<>(Arrays.asList(Globals.buildingBlocks.getLast())));;
//        for (int i = 0; i < blocks.size(); i++) {
//            int row = i / blocksPerRow;
//            int col = i % blocksPerRow;
//            blocks.get(i).setSize(200);
//            blocks.get(i).setPosition(new Coordinate(20, 20));
//            blocks.get(i).setColor(Color.RED);
//            blocks.get(i).draw();
//            gridPane.add(blocks.get(i), col, row);
////            this.getChildren().add(blocks.get(i));
//        }
//
//        ScrollPane scrollPane = new ScrollPane(gridPane);
//        scrollPane.setPrefSize(width, height);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setPannable(true);
//        scrollPane.setStyle("-fx-background: transparent;" + "-fx-background-color: rgba(217, 217, 217, 0.5);");
//        this.getChildren().add(scrollPane);

        this.primaryStage.getScene().setRoot(this);
    }

    public void pausing(boolean pausing) {
        this.clock.setPausing(pausing);
    }

    public void playRound() {
        this.playing = true;
        this.dice.setInteractable(true);

        Vector <BuildingBlock> block = this.blockGenerator.generateRandomBuildingBlocks(numBlock);
        this.blockContainer.setBlocks(block);

        this.initCards();
    }

    public void EndRound() {
        this.playing = false;
        this.clock.setRunning(false);
        if (userID == Globals.app.getUsers().size() - 1) {
            userID = 0;
        } else {
            userID++;
            this.updateUserInforText();
        }
        this.playRound();
    }

    private void updateUserInforText() {
        User currentUser = Globals.app.getUsers().get(this.userID);
        String userPointT = String.valueOf(currentUser.getPoint());
        this.userPointText.setText(userPointT);
        this.userPointText.setFill(Color.YELLOW);
        this.userPointText.setLayoutX(910 - userPointText.getBoundsInLocal().getWidth());
        this.userPointText.setLayoutY(120);

        this.userIDText.setText(currentUser.getName());
        this.userIDText.setFill(Color.RED);
        this.userIDText.setLayoutX(490);
        this.userIDText.setLayoutY(120);
    }
}