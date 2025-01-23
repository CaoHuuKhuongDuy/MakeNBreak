package com.models;

import com.commons.Coordinate;
import com.models.components.Arrow;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Clock extends Entity {
    private int time; // seconds
    private Arrow arrow;
    private AtomicBoolean pausing, running, interrupted;

    public Clock(Coordinate position) {
        super(position, true);
        this.time = 0;
        this.pausing = new AtomicBoolean(false);
        this.running = new AtomicBoolean(false);
        this.interrupted = new AtomicBoolean(false);
    }

    public void reset() {
        this.time = 0;
        this.interrupted.set(true);
        this.running.set(false);
        this.pausing.set(false);
        this.draw();
    }

    public void draw() {
        this.getChildren().clear();

        ImageView imageViewDice = new ImageView(new Image("/resources/assets/images/clock.png"));
        imageViewDice.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        // 15 -> 165 = 120s => 1s = 1.25
        // 165 -> 180 = 60s => 1s = 0.25
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
