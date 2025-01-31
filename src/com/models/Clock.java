/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a clock in the game. Manages the countdown timer and updates the
 * visual representation of the clock's arrow. Provides functionality to start, pause, and
 * reset the timer.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models;

import com.commons.Coordinate; // Represents a coordinate in the game grid.
import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.models.components.Arrow; // Represents the arrow component of the clock.
import javafx.application.Platform; // Provides platform-specific functionality.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.

import java.util.concurrent.atomic.AtomicBoolean; // Provides atomic operations for thread-safe variables.

public class Clock extends Entity {
    private int time; // The remaining time in seconds.
    private Arrow arrow; // The arrow component of the clock.
    private AtomicBoolean pausing, running, interrupted; // Thread-safe flags for controlling the clock.

    /**
     * Constructs a Clock with the specified position.
     *
     * @param position The position of the clock.
     */
    public Clock(Coordinate position) {
        super(position, true);
        this.time = 0;
        this.pausing = new AtomicBoolean(false);
        this.running = new AtomicBoolean(false);
        this.interrupted = new AtomicBoolean(false);
    }

    /**
     * Resets the clock to its initial state.
     */
    public void reset() {
        this.time = 0;
        this.interrupted.set(true);
        this.running.set(false);
        this.pausing.set(false);
        this.draw();
    }

    @Override
    public void draw() {
        // Clear the current children and set up the clock's visual components.
        this.getChildren().clear();

        ImageView imageViewDice = new ImageView(new Image(Globals.getResource("/resources/assets/images/Clock.png")));
        imageViewDice.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        // Initialize the arrow and add it to the clock.
        this.arrow = new Arrow(new Coordinate(this.position.x - 95, this.position.y - 59), 35, 15);
        this.getChildren().addAll(imageViewDice, arrow);
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setPausing(boolean pausing) {
        this.pausing.set(pausing);
    }

    public void setRunning(boolean running) {
        this.running.set(running);
    }

    /**
     * Starts the countdown timer and updates the clock's arrow.
     *
     * @param callback The callback to execute when the timer reaches zero.
     */
    public void startCounting(Runnable callback) {
        if (this.running.get()) {
            return;
        }
        this.running.set(true);
        this.interrupted.set(false);
        double currentAngle = Math.min(this.time, 60) * 0.25 + Math.max(0, this.time - 60) * 1.25;
        this.arrow.setAngle(180 - currentAngle);

        Thread thread = new Thread(() -> {
            try {
                while (this.time > 0 && this.running.get()) {
                    while (this.pausing.get()) {}

                    Thread.sleep(1000); // Simulate time decrement
                    this.time--;

                    // Update arrow angle
                    double newAngle = this.arrow.getAngle() + (this.arrow.getAngle() < 165 ? 1.25 : 0.25);
                    double clampedAngle = Math.min(180, newAngle); // Optional safety
                    Platform.runLater(() -> this.arrow.setAngle(clampedAngle));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.running.set(false);
                if (!this.interrupted.get()) Platform.runLater(callback); // Run callback on the JavaFX Application Thread
            }
        });

        thread.setDaemon(true); // Optional: Set the thread as a daemon thread
        thread.start();
    }
}