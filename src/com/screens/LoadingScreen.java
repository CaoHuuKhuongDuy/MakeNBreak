package com.screens;

import com.commons.Coordinate;
import com.commons.Globals;
import com.models.components.LoadingBar;
import com.controllers.mouse.SwitchScreen;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoadingScreen extends Screen {

    private SwitchScreen switchScreen;
    private LoadingBar loadingBar;

    public LoadingScreen(Stage primaryStage) {
        super(primaryStage);
        this.loadingBar = new LoadingBar(new Coordinate(256, 646), 512, 48);
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
        Image titleImage = new Image(Globals.getResource("/resources/assets/images/titleSlanted.png"));
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setLayoutY(30);

        ImageView instructionText = new ImageView(new Image(Globals.getResource("/resources/assets/images/text.png")));
        instructionText.setLayoutX(154);
        instructionText.setLayoutY(595);

        // Add loading bar
        //LoadingBar loadingBar = new LoadingBar(new Coordinate(256, 646), 512, 48);
        loadingBar.configureTimeline(() -> {
            switchScreen.setScreen(new GameScreen(primaryStage)).run();
        });

        this.getChildren().addAll(loadingBar.getProgressBar(), titleImageView, instructionText);

        this.primaryStage.getScene().setRoot(this);
        loadingBar.startAnimation();
    }
}


