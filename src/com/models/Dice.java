package com.models;

import com.commons.Coordinate;
import com.commons.Globals;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Dice extends Entity {
    private int value;
    private boolean rolling;
    ImageView imageViewDice;

    public Dice() {
        super();
        this.value = 0;
        this.rolling = false;
    }

    public Dice(Coordinate position, double width, double height) {
        this(position, width, height, true);
    }

    public Dice(Coordinate position, double width, double height, boolean interactable) {
        super(position, interactable, width, height);
        this.value = 0;
        this.rolling = false;
    }

    public void draw() {
        this.getChildren().clear();
        imageViewDice = new ImageView(new Image(Globals.getResource("/resources/assets/images/diceButton.png")));
        imageViewDice.setFitHeight(this.height);
        imageViewDice.setFitWidth(this.width);
        imageViewDice.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        this.getChildren().add(imageViewDice);
    }

    public void reset() {
        this.rolling = false;
        this.value = 0;
        this.draw();
    }

    public boolean isRolling() {
        return this.rolling;
    }

    public void setRolling(boolean rolling) {
        this.rolling = rolling;
    }

    public void setImageView(String url) {
        this.imageViewDice.setImage(new Image(url));
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
