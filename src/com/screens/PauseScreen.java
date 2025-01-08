package com.screens;

import com.commons.Coordinate;
import com.commons.GameType;
import com.commons.Globals;
import com.controllers.mouse.SwitchScreen;
import com.models.Card;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;


public class PauseScreen extends Screen {

    public PauseScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
    }

    @Override
    public void initHandlers() {

    }

    @Override
    public void display() {
        this.getChildren().clear();

        // Create Pause screen
        this.setPrefSize(1025, 768);
        ImageView imageViewBackground = new ImageView(new Image("/resources/assets/images/background.png"));
        imageViewBackground.setFitWidth(1024);
        imageViewBackground.setFitHeight(786);
        this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        ImageView imageViewOuterPanel = new ImageView(new Image("/resources/assets/images/OuterPanel.png"));
        imageViewOuterPanel.setFitWidth(371);
        imageViewOuterPanel.setFitHeight(381);

        ImageView imageViewInnerPanel = new ImageView(new Image("/resources/assets/images/InnerPanel.png"));
        imageViewOuterPanel.setFitWidth(323);
        imageViewOuterPanel.setFitHeight(280);

        Button quitButton = new Button();
        ImageView imageViewQuitButton = new ImageView(new Image("/resources/assets/images/quitButton.png"));
        imageViewQuitButton.setFitWidth(130);
        imageViewQuitButton.setFitHeight(50);
        quitButton.setGraphic(imageViewQuitButton);
        quitButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        Button resumeButton = new Button();
        ImageView imageViewResumeButton = new ImageView(new Image("/resources/assets/images/quitButton.png"));
        imageViewResumeButton.setFitWidth(130);
        imageViewResumeButton.setFitHeight(50);
        resumeButton.setGraphic(imageViewResumeButton);
        resumeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");


        imageViewOuterPanel.setLayoutX(314);
        imageViewOuterPanel.setLayoutY(193);

        imageViewInnerPanel.setLayoutX(24);
        imageViewInnerPanel.setLayoutY(77);

        quitButton.setLayoutX(37.5);
        quitButton.setLayoutY(5.21);

        resumeButton.setLayoutX(37.5);
        resumeButton.setLayoutY(5.21);

        this.getChildren().addAll(imageViewBackground, imageViewOuterPanel, imageViewInnerPanel, quitButton, quitButton);

        // this.setVisible(false);

        this.primaryStage.getScene().setRoot(this);
    }
}