package com.screens;

import com.commons.Globals;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public abstract class Screen extends Pane {
    protected String title;
    protected Stage primaryStage;

    public Screen(Stage primaryStage) {
        this(primaryStage, "");
    }

    public Screen(Stage primaryStage, String title) {
        this.primaryStage = primaryStage;
        this.title = title;
        setupBackground("/resource/assets/image/background.png");
        setPrefSize(Globals.DEFAULT_WIDTH, Globals.DEFAULT_HEIGHT);
    }

    // For setting a specific background in any screen
    protected void setupBackground(String imagePath) {
        Image backgroundImage = new Image(imagePath);

        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                null
        );

        setBackground(new Background(bgImage));
    }

    public abstract void display();
    public abstract void initHandlers();
}
