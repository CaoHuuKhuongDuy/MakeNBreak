/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a dice in the game. Manages the dice's value and visual representation.
 * Provides functionality to roll the dice and reset its state.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models;

import com.commons.Coordinate; // Represents a coordinate in the game grid.
import com.commons.Globals; // Provides global constants and utility methods for the application.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.

public class Dice extends Entity {
    private int value; // The current value of the dice.
    private boolean rolling; // Whether the dice is currently rolling.
    ImageView imageViewDice; // The image view for the dice.

    /**
     * Constructs a Dice with the specified position, width, height, and interactability.
     *
     * @param position     The position of the dice.
     * @param width        The width of the dice.
     * @param height       The height of the dice.
     * @param interactable Whether the dice is interactable.
     */
    public Dice(Coordinate position, double width, double height, boolean interactable) {
        super(position, interactable, width, height);
        this.value = 0;
        this.rolling = false;
    }

    @Override
    public void draw() {
        // Clear the current children and set up the dice's visual representation.
        this.getChildren().clear();
        imageViewDice = new ImageView(new Image(Globals.getResource("/resources/assets/images/Button/diceButton.png")));
        imageViewDice.setFitHeight(this.height);
        imageViewDice.setFitWidth(this.width);
        imageViewDice.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        this.getChildren().add(imageViewDice);
    }

    /**
     * Resets the dice to its initial state.
     */
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

    /**
     * Updates the image of the dice.
     *
     * @param url The URL of the new image.
     */
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