package com.screens;

import com.models.components.CustomButton;
import com.controllers.mouse.SwitchScreen;
import javafx.stage.Stage;

public class InstructionScreen extends Screen {

    private SwitchScreen switchScreen;

    public InstructionScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
        setupBackground("/resources/assets/images/instructions.png");
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        this.getChildren().clear();

        CustomButton exitButton = new CustomButton("", "/resources/assets/images/Icon_Cross.png");
        exitButton.setOnMouseClicked(this.switchScreen.setScreen(new MainScreen(primaryStage)));

        exitButton.setLayoutX(973);
        exitButton.setLayoutY(20);

        this.getChildren().addAll(exitButton);

        this.primaryStage.getScene().setRoot(this);
    }
}
