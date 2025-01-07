package com.screens;

import com.controllers.mouse.SwitchScreen;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class IntroScreen extends Screen {

    private SwitchScreen switchScreen;

    public IntroScreen(Stage primaryStage) {
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

        Image titleImage = new Image("/resources/assets/images/titleSlanted.png");
        ImageView titleImageView = new ImageView(titleImage);

        // Create the buttons
        Button mainScreenButton = new Button();
        ImageView imageViewButton1 = new ImageView(new Image("/resources/assets/images/startButton.png"));
        mainScreenButton.setGraphic(imageViewButton1);
        mainScreenButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        Button button2 = new Button();
        ImageView imageViewButton2 = new ImageView(new Image("/resources/assets/images/quitButton.png"));
        button2.setGraphic(imageViewButton2);
        button2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        // Add event handlers for buttons
        mainScreenButton.setOnAction(event -> {
            // Your action for button 1
            System.out.println("Button 1 clicked");
        });

        button2.setOnAction(event -> {
            // Your action for button 1
            System.out.println("Button 2 clicked");
            Platform.exit();
        });

        mainScreenButton.setOnMouseClicked(this.switchScreen.setScreen(new GameScreen(primaryStage)));

        titleImageView.setLayoutX(0);
        titleImageView.setLayoutY(30);

        mainScreenButton.setLayoutX(413);
        mainScreenButton.setLayoutY(573);

        button2.setLayoutX(413);
        button2.setLayoutY(651);

        // Add all the elements to the Pane
        this.getChildren().addAll(titleImageView, mainScreenButton, button2);


        this.primaryStage.getScene().setRoot(this);
    }
}
