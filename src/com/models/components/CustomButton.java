package com.models.components;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ContentDisplay;

public class CustomButton extends Button {
    public CustomButton(String text, String imagePath) {
        super(text); // Set the text of the button

        // Load the image and set it as the graphic of the button
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        this.setGraphic(imageView);

        // Apply the stylesheet for custom styling
        this.getStylesheets().add("resources/assets/styles/Button.css");

        // Set content display to center (image and text will be centered)
        this.setContentDisplay(ContentDisplay.CENTER);
    }
    public CustomButton(String text, String imagePath, String color) {
        super(text); // Set the text of the button

        // Load the image and set it as the graphic of the button
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        this.setGraphic(imageView);

        // Apply the stylesheet for custom styling
        this.getStylesheets().add("resources/assets/styles/Button.css");

        // Set content display to center (image and text will be centered)
        this.setContentDisplay(ContentDisplay.CENTER);

        // Modify the text style after the UI is initialized
        Platform.runLater(() -> {
            // Lookup and apply the style to the text node after initialization
            this.lookup(".text").setStyle("-fx-fill: " + "color" + ";"); // Set the text color to red
        });
    }
}
