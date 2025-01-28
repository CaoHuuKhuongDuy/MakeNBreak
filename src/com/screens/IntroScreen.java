package com.screens;

import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.components.CustomButton;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class IntroScreen extends Screen {

    private SwitchScreen switchScreen;

    public IntroScreen(Stage primaryStage) {
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

        Image titleImage = new Image(Globals.getResource("/resources/assets/images/titleSlanted.png"));
        ImageView titleImageView = new ImageView(titleImage);

        CustomButton startButton = new CustomButton("START", "/resources/assets/images/Button/yellowButton.png");

        CustomButton quitButton = new CustomButton("QUIT", "/resources/assets/images/Button/redButton.png");

        quitButton.setOnAction(event -> {
            Platform.exit();
        });

        startButton.setOnMouseClicked(this.switchScreen.setScreen(new InstructionScreen(primaryStage)));

        titleImageView.setLayoutX(0);
        titleImageView.setLayoutY(30);

        startButton.setLayoutX(408);
        startButton.setLayoutY(573);

        quitButton.setLayoutX(408);
        quitButton.setLayoutY(651);

        this.getChildren().addAll(titleImageView, startButton, quitButton);
        this.primaryStage.getScene().setRoot(this);
    }
}
