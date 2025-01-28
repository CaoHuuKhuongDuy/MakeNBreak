package com.screens;

import com.commons.Globals;
import com.controllers.mouse.ShowPopup;
import com.controllers.mouse.SwitchScreen;
import com.models.components.CustomButton;
import javafx.stage.Stage;


public class InstructionPopup extends Screen {

    private ShowPopup showPopup;
    private SwitchScreen switchScreen;
    private Screen previousScreen;


    public InstructionPopup(Stage primaryStage, Screen previousScreen) {
        super(primaryStage);
        this.previousScreen = previousScreen;
        this.initHandlers();
        this.display();
        setupBackground("/resources/assets/images/instructions.png");
    }

    @Override
    public void initHandlers() {
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(previousScreen);
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        this.getChildren().clear();

        CustomButton returnButton = new CustomButton("", "/resources/assets/images/Icon_Cross.png");
        returnButton.setOnMouseClicked(this.showPopup.setPopUpScreen(this).setVisible(false));

        returnButton.setLayoutX(973);
        returnButton.setLayoutY(20);

        this.getChildren().addAll(returnButton);

        this.primaryStage.getScene().setRoot(this);
    }
}
