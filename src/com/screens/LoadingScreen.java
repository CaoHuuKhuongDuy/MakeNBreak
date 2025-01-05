package com.screens;

import com.models.components.LoadingBar;
import com.controllers.mouse.SwitchScreen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoadingScreen extends Screen {

    private LoadingBar loadingBar;
    private SwitchScreen switchScreen;

    public LoadingScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
    }


    @Override
    public void display() {
        this.getChildren().clear();

        // Add title image
        Image titleImage = new Image("/resource/assets/image/titleSlanted.png");
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setLayoutY(30);

        // Add loading bar
        this.loadingBar = new LoadingBar(512, 48);
        this.loadingBar.configureTimeline(() -> {
            switchScreen.setScreen(new MainScreen(primaryStage)).run();
        });

        // Position progress bar
        loadingBar.getProgressBar().setLayoutY(646);
        loadingBar.getProgressBar().setLayoutX(256);

        this.getChildren().addAll(loadingBar.getProgressBar(), titleImageView);

        this.primaryStage.getScene().setRoot(this);
        loadingBar.startAnimation();
    }
}
