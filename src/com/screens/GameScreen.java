package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.Card;
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
        this.layout.getChildren().clear();

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

        generateCardButton.setLayoutX(750);
        generateCardButton.setLayoutY(800);

        this.layout.getChildren().addAll(backButton, generateCardButton);

        this.primaryStage.getScene().setRoot(layout);
    }

    private void regenerateCard() {
        cardNumber++;
        if (currentCard != null) {
            this.layout.getChildren().remove(currentCard);
        }

        Card newCard1 = new  Card(Globals.listBuildingBlock.generateBuilding(20, 20, 10), new Coordinate(700, 50), 400, 250, cardNumber);
        Card newCard2 = new  Card(Globals.listBuildingBlock.generateBuilding(20, 20, 10), new Coordinate(1200, 50), 400, 250, cardNumber);

        newCard1.draw();
        newCard2.draw();

        currentCard = newCard1;
        this.layout.getChildren().add(currentCard);
        this.layout.getChildren().add(newCard2);
    }

}
