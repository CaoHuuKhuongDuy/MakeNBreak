package com.screens;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        progressBar.setStyle("-fx-accent: green;"); // Optional: Change the color of the progress bar
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

    }

    @Override
    public void display() {
        this.getChildren().clear();

        // Create layout (e.g., StackPane) and add the progress bar
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(progressBar);

        // Center the progress bar
        StackPane.setAlignment(progressBar, javafx.geometry.Pos.CENTER);

        // Add the StackPane to the screen
        this.getChildren().add(stackPane);

        // Set the scene root
        this.primaryStage.getScene().setRoot(this);

        // Start the animation
        timeline.play();
    }
}

