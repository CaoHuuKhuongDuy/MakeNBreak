package com.screens;

import com.commons.Coordinate;
import com.commons.GameType;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.Card;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;


public class GameScreen extends Screen {
    private SwitchScreen switchScreen;
    private Card currentCard;

    public GameScreen(Stage primaryStage) {
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

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        backButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));

        Button generateCardButton = new Button("Generate Card");
        generateCardButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        generateCardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                regenerateCard();
            }
        });

        // Create Timer Button
        Button timerButton = new Button();
        ImageView imageViewTimerButton1 = new ImageView(new Image("/resources/assets/images/timer.png"));
        timerButton.setGraphic(imageViewTimerButton1);
        timerButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");


        // Set initial image sizes
        double imageSize = 66;

        // Create Dice Button
        Button diceButton = new Button();
        ImageView imageViewDiceButton = new ImageView(new Image("/resources/assets/images/diceButton.png"));
        imageViewDiceButton.setFitHeight(imageSize);
        imageViewDiceButton.setFitWidth(imageSize);
        diceButton.setGraphic(imageViewDiceButton);
        diceButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        diceButton.setPrefSize(imageSize, imageSize);

        diceButton.setOnMouseClicked(event -> {
            Random random = new Random();
            int diceValue = random.nextInt(3) + 1; // Generate a random number between 1 and 3
            String imagePath = String.format("/resources/assets/images/Dice%ds.png", diceValue);
            imageViewDiceButton.setImage(new Image(imagePath)); // Update the image
        });

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

        // Create pause screen
//        PauseScreen pauseScreen = new PauseScreen(primaryStage);
//        this.getChildren().add(pauseScreen);
//        iconSettingButton.setOnMouseClicked(even -> {
//            pauseScreen.display();
//            pauseScreen.setVisible(true);
//        });
//        pauseScreen.display();

        iconSettingButton.setOnMouseClicked(event -> {
            System.out.println("Setting button press");
            new SwitchScreen(primaryStage, new PauseScreen(primaryStage)).run(); // Switch to the PauseScreen when clicked
        });

        generateCardButton.setLayoutX(75);
        generateCardButton.setLayoutY(0);

        timerButton.setLayoutX(133);
        timerButton.setLayoutY(92);

        diceButton.setLayoutX(41);
        diceButton.setLayoutY(99);

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

        this.getChildren().addAll(backButton, generateCardButton, timerButton, diceButton, kickButton, playBoard,
                scoreRectangle, iconCoin, iconPlayer, blockRectangle, iconSettingButton);


        this.primaryStage.getScene().setRoot(this);
    }

    private void regenerateCard() {
        if (currentCard != null) this.getChildren().remove(currentCard);

        Card newCard1 = new  Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, GameType.MULTIPLE_BLOCK), new Coordinate(70, 50), 261, 174, GameType.MULTIPLE_BLOCK);
        Card newCard2 = new  Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, GameType.SINGLE_BLOCK), new Coordinate(70, 320), 261, 174, GameType.SINGLE_BLOCK);

        newCard1.draw();
        newCard2.draw();

        currentCard = newCard1;
        this.getChildren().add(currentCard);
        this.getChildren().add(newCard2);
    }

}