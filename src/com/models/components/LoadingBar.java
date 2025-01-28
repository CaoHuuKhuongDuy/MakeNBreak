package com.models.components;

import com.commons.Globals;
import com.models.Entity;
import com.commons.Coordinate;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class LoadingBar extends Entity {

    private ProgressBar progressBar;
    private Timeline timeline;

    public LoadingBar(Coordinate position, double width, double height) {
        super(position, false, width, height);
        this.progressBar = new ProgressBar(0);
        this.progressBar.setPrefWidth(width);
        this.progressBar.setLayoutX(position.x);
        this.progressBar.setLayoutY(position.y);
        this.progressBar.getStylesheets().add(Globals.getResource("/resources/assets/styles/progressBar.css"));

        this.draw();
    }

    @Override
    public void draw() {
        this.getChildren().clear();
        this.getChildren().add(progressBar);
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
