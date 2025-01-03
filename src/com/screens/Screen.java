package com.screens;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public abstract class Screen extends Pane {
    protected String title;
    protected Stage primaryStage;

    public Screen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.title = "";
        setupBackground();
    }

    public Screen(Stage primaryStage, String title) {
        this.primaryStage = primaryStage;
        this.title = title;
        setupBackground();
    }

    private void setupBackground() {

        BackgroundSize backgroundSize = new BackgroundSize(
                1024,   // Width of the background
                768,   // Height of the background
                false, // Scale to width (false means it won't stretch)
                false, // Scale to height (false means it won't stretch)
                true,  // Keep aspect ratio (true means it maintains original aspect ratio)
                false  // No repeat for background
        );
        setBackground(new Background(new BackgroundImage(
                new Image("/resource/assets/image/background.png"), // Provide the path to your image
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize)
        ));
        setPrefSize(1024, 768);
    }

    public abstract void display();
    public abstract void initHandlers();
}
