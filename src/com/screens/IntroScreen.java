package com.screens;

import com.controllers.mouse.SwitchScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
        this.getChildren().removeIf(node -> this.getChildren().indexOf(node) > 0); //removes children with an index greater than 0 (skipping the background at index 0).


        //Image image = new Image("/resource/assets/image/background.png");
        // Replace with actual image path
        //ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(1024); // Set the image width
        //imageView.setFitHeight(768);
        //imageView.setPreserveRatio(true);

        Image titleImage = new Image("/resource/assets/image/titleSlanted.png");
        ImageView titleImageView = new ImageView(titleImage);
        //titleImageView.setFitWidth(5118); // Set the image width
        //titleImageView.setFitHeight(4911);
        //titleImageView.setPreserveRatio(true);

        // Create the buttons
        Button button1 = new Button();
        ImageView imageViewButton1 = new ImageView(new Image("/resource/assets/image/StartButton.png"));
        button1.setGraphic(imageViewButton1);
        button1.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        Button button2 = new Button("Button 2");

        // Add event handlers for buttons
        button1.setOnAction(event -> {
            // Your action for button 1
            System.out.println("Button 1 clicked");
        });

        button1.setOnMouseClicked(this.switchScreen.setScreen(new LoadingScreen(primaryStage)));

        StackPane.setAlignment(button1, Pos.CENTER);
        StackPane.setMargin(button1, new Insets(400, 0, 0, 0)); // Move button slightly up if needed

        StackPane.setAlignment(titleImageView, Pos.TOP_CENTER);
        StackPane.setMargin(titleImageView, new Insets(30, 0, 0, 0));

        this.getChildren().addAll(titleImageView, button1);


        this.primaryStage.getScene().setRoot(this);
    }
}
