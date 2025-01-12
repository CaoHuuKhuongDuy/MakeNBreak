package com.models;

import com.commons.Coordinate;
import com.models.components.Arrow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.atomic.AtomicInteger;

public class Clock extends Entity {
    private int time; // seconds
    private Arrow arrow;
    private AtomicInteger running;

    public Clock() {
        super();
        this.time = 0;
        this.running = new AtomicInteger(0);
    }

    public Clock(Coordinate position) {
        super(position, true);
        this.time = 0;
        this.running = new AtomicInteger(0);
    }

    public Clock(Coordinate position, double width, double height, int time) {
        super(position, true, width, height);
        this.time = time;
        this.running = new AtomicInteger(0);
    }


    public Clock(Coordinate position, double width, double height) {
        super(position, true, width, height);
        this.time = 0;
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

    public void startCounting() {
        this.running.incrementAndGet();
        double currentAngle = Math.min(this.time, 60) * 0.25 + Math.max(0, this.time - 60) * 1.25;
        this.arrow.setAngle(180 - currentAngle);
        Thread thread = new Thread(() -> {
            while (this.time > 0 && this.running.get() == 1) {
                try {
                    Thread.sleep(1000);
                    this.time--;
                    if (this.arrow.getAngle() < 165) {
                        this.arrow.setAngle(this.arrow.getAngle() + 1.25);
                    } else {
                        this.arrow.setAngle(this.arrow.getAngle() + 0.25);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.running.decrementAndGet();
        });
        thread.start();
    }
}
