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
        this.getChildren().clear(); //removes children with an index greater than 0 (skipping the background at index 0).


        //Image image = new Image("/resource/assets/image/background.png");
        // Replace with actual image path
        //ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(1024); // Set the image width
        //imageView.setFitHeight(768);
        //imageView.setPreserveRatio(true);

        Image titleImage = new Image("/resource/assets/image/titleSlanted.png");
        ImageView titleImageView = new ImageView(titleImage);

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

        titleImageView.setLayoutX(0); // Center alignment on X-axis (in a Pane, you'd need to manually adjust for centering)
        titleImageView.setLayoutY(30); // Adjust Y-position

        button1.setLayoutX(413); // Adjust X-position for button1
        button1.setLayoutY(573); // Adjust Y-position for button1

        // Add all the elements to the Pane
        this.getChildren().addAll(titleImageView, button1);


        this.primaryStage.getScene().setRoot(this);
    }
}
