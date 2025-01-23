package com.screens;

import com.controllers.mouse.ShowPopup;
import com.controllers.mouse.SwitchScreen;
import com.models.components.CustomButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class PauseScreen extends Screen {

    private ShowPopup showPopup;
    private SwitchScreen switchScreen;
    private Screen previousScreen;

    public PauseScreen(Stage primaryStage, Screen previousScreen) {
        super(primaryStage);
        this.previousScreen = previousScreen;
        this.initHandlers();
        this.display();
    }

    @Override
    public void initHandlers() {
        this.showPopup = new ShowPopup(primaryStage).setCurrentScreen(previousScreen);
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {

        this.getChildren().clear();

        // Create a semi-transparent black rectangle
        Rectangle overlay = new Rectangle(1024, 768);
        overlay.setFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5)); // Black with 50% opacity

        // Add outer panel
        ImageView imageViewSettingPanel = new ImageView(new Image("/resources/assets/images/OuterPanel.png"));
        imageViewSettingPanel.setFitWidth(371);
        imageViewSettingPanel.setFitHeight(381);

        ImageView panelBackground = new ImageView(new Image("resources/assets/images/meme.png"));
        panelBackground.setLayoutX(253);
        panelBackground.setLayoutY(286);

        // Create quit button
        CustomButton quitButton = new CustomButton("QUIT", "resources/assets/images/Button/redButton.png");
        quitButton.setSize(130, 55);
        quitButton.setOnMouseClicked(this.switchScreen.setScreen(new IntroScreen(primaryStage)));
        
        CustomButton resumeButton = new CustomButton("RESUME", "resources/assets/images/Button/yellowButton.png");
        resumeButton.setOnMouseClicked(this.showPopup.setPopUpScreen(this).setVisible(false));
        resumeButton.setSize(130, 55);


        // Create cross button
        CustomButton crossButton = new CustomButton("", "resources/assets/images/Icon_Cross.png");
        crossButton.setOnMouseClicked(this.showPopup.setPopUpScreen(this).setVisible(false));

        imageViewSettingPanel.setLayoutX(314);
        imageViewSettingPanel.setLayoutY(193);

        quitButton.setLayoutX(358);
        quitButton.setLayoutY(474);

        resumeButton.setLayoutX(512);
        resumeButton.setLayoutY(474);

        crossButton.setLayoutX(637);
        crossButton.setLayoutY(220);

        this.getChildren().addAll(overlay, imageViewSettingPanel, panelBackground, quitButton, resumeButton, crossButton);
        System.out.println("Pause screen components added.");


        this.primaryStage.getScene().setRoot(this);
    }
}