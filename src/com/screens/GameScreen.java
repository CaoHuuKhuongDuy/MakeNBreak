package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.entities.Card;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;


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
        // Clear any existing children in case the method is called multiple times
        this.layout.getChildren().clear();

        // Create buttons
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        backButton.setOnMouseClicked(this.switchScreen.handler(new MainScreen(primaryStage)));

        // Hover effect for back button
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;"));

        // Create Generate Card Button
        Button generateCardButton = new Button("Generate Card");
        generateCardButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;");
        generateCardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                regenerateCard();
            }
        });

        // Set position for generate card button
        generateCardButton.setLayoutX(750);
        generateCardButton.setLayoutY(800);

        // Hover effect for generate card button
        generateCardButton.setOnMouseEntered(e -> generateCardButton.setStyle("-fx-background-color: #FF8C42; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;"));
        generateCardButton.setOnMouseExited(e -> generateCardButton.setStyle("-fx-background-color: #FF5733; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 10px;"));

        // Add buttons to the layout (Pane)
        this.layout.getChildren().addAll(backButton, generateCardButton);

        this.primaryStage.getScene().setRoot(layout);
    }

    private void regenerateCard() {
        // If there is an existing card, remove it from the layout
        if (currentCard != null) {
            this.layout.getChildren().remove(currentCard);
        }

        // Create a new card with the desired properties
        Card newCard1 = new  Card(Globals.listBuildingBlock.generateBuilding(20, 20, 10), new Coordinate(700, 50), 400, 250);
        Card newCard2 = new  Card(Globals.listBuildingBlock.generateBuilding(20, 20, 10), new Coordinate(1200, 50), 400, 250);

        // Draw the new card
        newCard1.draw();
        newCard2.draw();

        // Set the new card reference and add it to the layout
        currentCard = newCard1;
        this.layout.getChildren().add(currentCard);
        this.layout.getChildren().add(newCard2);
    }

}
