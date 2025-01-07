package com.models.components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class LoadingBar {

    private ProgressBar progressBar;
    private Timeline timeline;

    public LoadingBar(double width, double height) {
        this.progressBar = new ProgressBar(0);
        this.progressBar.setPrefWidth(width);
        this.progressBar.setPrefHeight(height);
        this.progressBar.getStylesheets().add("/resources/assets/styles/progressBar.css");
    }

    public void configureTimeline(Runnable onFinish) {
        timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(progressBar.progressProperty(), 0.25)),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.5)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(progressBar.progressProperty(), 0.75)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progressBar.progressProperty(), 1.0))
        );

        // Set action when timeline finishes
        timeline.setOnFinished(event -> onFinish.run());
    }

    public void startAnimation() {
        if (timeline != null) {
            timeline.play();
        }
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}
