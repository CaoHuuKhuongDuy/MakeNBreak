package com.models.components;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ContentDisplay;

public class CustomButton extends Button {
    private ImageView imageView;
    public CustomButton(String text, String imagePath) {
        super(text);

        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        this.setGraphic(imageView);


        this.getStylesheets().add("resources/assets/styles/Button.css");


        this.setContentDisplay(ContentDisplay.CENTER);
    }

    public void setSize(double width, double height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
}
