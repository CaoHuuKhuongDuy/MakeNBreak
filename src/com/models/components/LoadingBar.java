/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a loading bar component. Manages the progress bar animation and
 * triggers a callback when the loading is complete.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models.components;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.models.Entity; // Base class for game entities.
import com.commons.Coordinate; // Represents a coordinate in the game grid.
import javafx.animation.KeyFrame; // Represents a keyframe in an animation.
import javafx.animation.KeyValue; // Represents a key value in an animation.
import javafx.animation.Timeline; // Represents a timeline for animations.
import javafx.scene.control.ProgressBar; // Represents a progress bar in JavaFX.
import javafx.util.Duration; // Represents a duration of time.

public class LoadingBar extends Entity {

    private ProgressBar progressBar; // The progress bar component.
    private Timeline timeline; // The timeline for the loading animation.

    /**
     * Constructs a LoadingBar with the specified position, width, and height.
     *
     * @param position The position of the loading bar.
     * @param width    The width of the loading bar.
     * @param height   The height of the loading bar.
     */
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
        // Clear the current children and add the progress bar.
        this.getChildren().clear();
        this.getChildren().add(progressBar);
    }

    /**
     * Configures the timeline for the loading animation.
     *
     * @param onFinish The callback to execute when the loading is complete.
     */
    public void configureTimeline(Runnable onFinish) {
        timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(progressBar.progressProperty(), 0.25)),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.5)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(progressBar.progressProperty(), 0.75)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progressBar.progressProperty(), 1.0))
        );

        // Set the action to execute when the timeline finishes.
        timeline.setOnFinished(event -> onFinish.run());
    }

    /**
     * Starts the loading animation.
     */
    public void startAnimation() {
        if (timeline != null) {
            timeline.play();
        }
    }

    /**
     * Returns the progress bar component.
     *
     * @return The progress bar.
     */
    public ProgressBar getProgressBar() {
        return progressBar;
    }
}