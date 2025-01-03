package com.screens;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Screen extends StackPane {
    protected String title;
    protected Stage primaryStage;
    private ImageView background;

    public Screen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.title = "";
        setupBackground(); // Add background
    }

    public Screen(Stage primaryStage, String title) {
        this.primaryStage = primaryStage;
        this.title = title;
        setupBackground();
    }

    private void setupBackground() {
        // Load background image
        Image image = new Image("/resource/assets/image/background.png");
        background = new ImageView(image);
        background.setFitWidth(1024);
        background.setFitHeight(768);
        background.setPreserveRatio(false);

        this.getChildren().addFirst(background); // Always add background at index 0
    }

    public abstract void display();
    public abstract void initHandlers();
}
