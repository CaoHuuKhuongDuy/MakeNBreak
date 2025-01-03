package com.screens;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.controllers.action.SwitchScreen;


public class LoadingScreen extends Screen {
    private ProgressBar progressBar;
    private Timeline timeline;

    public LoadingScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {

        // Initialize progress bar
        progressBar = new ProgressBar(0);
        //progressBar.setStyle("-fx-accent: #A39B32;
        //                      -fx-border-color: #4D0000;
        //                      -fx-border-width: 3px;
        //                      -fx-background-color: transparent");
        progressBar.getStylesheets().add("/resource/assets/styles/progressBar.css");
        progressBar.setPrefWidth(512); // Set preferred width
        progressBar.setPrefHeight(28); // Set preferred height

        // Create a Timeline for loading animation
        timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                // KeyFrame 1: Increase to 25% at 1 second
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(progressBar.progressProperty(), 0.25)),

                // KeyFrame 2: Increase to 50% at 2 seconds
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(progressBar.progressProperty(), 0.5)),

                // KeyFrame 3: Increase to 75% at 3 seconds
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(progressBar.progressProperty(), 0.75)),

                // KeyFrame 4: Complete at 4 seconds
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(progressBar.progressProperty(), 1.0))
        );

        timeline.setOnFinished(new SwitchScreen(primaryStage, new MainScreen(primaryStage)));

    }

    @Override
    public void display() {
        this.getChildren().clear();

        Image titleImage = new Image("/resource/assets/image/titleSlanted.png");
        ImageView titleImageView = new ImageView(titleImage);

        // Center the progress bar
        progressBar.setLayoutX(256); // Center alignment on X-axis (in a Pane, you'd need to manually adjust for centering)
        progressBar.setLayoutY(646);

        titleImageView.setLayoutX(0); // Center alignment on X-axis (in a Pane, you'd need to manually adjust for centering)
        titleImageView.setLayoutY(30);

        // Add the StackPane to the screen
        this.getChildren().addAll(progressBar, titleImageView);

        // Set the scene root
        this.primaryStage.getScene().setRoot(this);

        // Start the animation
        timeline.play();
    }
}

