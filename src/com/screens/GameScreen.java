package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.Card;
import com.models.CardType;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class GameScreen extends Screen {
    private SwitchScreen switchScreen;
    private Card currentCard;
    private int cardNumber = 0;

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

        generateCardButton.setLayoutX(75);
        generateCardButton.setLayoutY(0);

        this.getChildren().addAll(backButton, generateCardButton);

        this.primaryStage.getScene().setRoot(this);
    }

    private void regenerateCard() {
        if (currentCard != null) this.getChildren().remove(currentCard);

        Card newCard1 = new  Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, CardType.MULTI), new Coordinate(70, 50), 261, 174, CardType.MULTI);
        Card newCard2 = new  Card(Globals.listBuildingBlock.generateBuilding(10, 15, 10, CardType.SINGLE), new Coordinate(70, 320), 261, 174, CardType.SINGLE);

        newCard1.draw();
        newCard2.draw();

        currentCard = newCard1;
        this.getChildren().add(currentCard);
        this.getChildren().add(newCard2);
    }

}