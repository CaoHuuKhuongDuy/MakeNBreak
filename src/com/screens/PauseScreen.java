package com.screens;

import com.controllers.mouse.ShowScreen;
import com.controllers.mouse.SwitchScreen;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class PauseScreen extends Screen {

    private ShowScreen showScreen;
    private SwitchScreen switchScreen;

    public PauseScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
        this.display();
    }

    @Override
    public void initHandlers() {
        this.showScreen = new ShowScreen(primaryStage);
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

        // Create quit button
        Button quitButton = new Button();
        ImageView imageViewQuitButton = new ImageView(new Image("/resources/assets/images/QuitButtonSetting.png"));
        imageViewQuitButton.setFitWidth(130);
        imageViewQuitButton.setFitHeight(55);
        quitButton.setGraphic(imageViewQuitButton);
        quitButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        quitButton.setOnMouseClicked(this.switchScreen.setScreen(new IntroScreen(primaryStage)));

        quitButton.setOnMouseClicked(event -> {
            new SwitchScreen(primaryStage, new IntroScreen(primaryStage)).run();
        });

        // Create resume button
        Button resumeButton = new Button();
        ImageView imageViewResumeButton = new ImageView(new Image("/resources/assets/images/ResumeButtonSetting.png"));
        imageViewResumeButton.setFitWidth(130);
        imageViewResumeButton.setFitHeight(55);
        resumeButton.setGraphic(imageViewResumeButton);
        resumeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        resumeButton.setOnMouseClicked(this.showScreen.setScreen(this).setVisible(false));


        // Create cross button
        Button crossButton = new Button();
        ImageView imageViewCrossButton = new ImageView(new Image("/resources/assets/images/Icon_Cross.png"));
        imageViewCrossButton.setFitWidth(26);
        imageViewCrossButton.setFitHeight(26);
        crossButton.setGraphic(imageViewCrossButton);
        crossButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        crossButton.setOnMouseClicked(this.showScreen.setScreen(this).setVisible(false));

        imageViewSettingPanel.setLayoutX(314);
        imageViewSettingPanel.setLayoutY(193);

        quitButton.setLayoutX(358);
        quitButton.setLayoutY(474);

        resumeButton.setLayoutX(512);
        resumeButton.setLayoutY(474);

        crossButton.setLayoutX(637);
        crossButton.setLayoutY(220);

        this.getChildren().addAll(overlay, imageViewSettingPanel, quitButton, resumeButton, crossButton);
        System.out.println("Pause screen components added.");


        this.primaryStage.getScene().setRoot(this);
    }
}