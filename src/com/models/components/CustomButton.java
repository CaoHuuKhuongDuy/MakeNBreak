/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a custom button in the UI. Extends the JavaFX `Button` class
 * to include an image and custom styling.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models.components;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import javafx.scene.control.Button; // Represents a button in JavaFX.
import javafx.scene.image.Image; // Represents an image that can be displayed.
import javafx.scene.image.ImageView; // Displays an image in the UI.
import javafx.scene.control.ContentDisplay; // Specifies how content is displayed within a button.

public class CustomButton extends Button {
    private ImageView imageView; // The image displayed on the button.

    /**
     * Creates a custom button with text and an image.
     *
     * @param text      The text to display on the button.
     * @param imagePath The path to the image to display on the button.
     */
    public CustomButton(String text, String imagePath) {
        super(text);
        imagePath = Globals.getResource(imagePath);
        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        this.setGraphic(imageView);
        this.getStylesheets().add(Globals.getResource("/resources/assets/styles/Button.css"));
        this.setContentDisplay(ContentDisplay.CENTER);
    }

    /**
     * Sets the size of the button and its image.
     *
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public void setSize(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
}