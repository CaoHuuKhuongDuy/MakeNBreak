package com.screens;

import com.controllers.mouse.SwitchScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

        Image image = new Image("file:assets/images/example.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);

        imageView.setPreserveRatio(true);
        this.getChildren().add(imageView);

        Image titleImage = new Image(getClass().getResource("/resource/assets/image/titleSlanted.png").toExternalForm());
        ImageView titleImageView = new ImageView(titleImage);
        //titleImageView.setFitWidth(5118); // Set the image width
        //titleImageView.setFitHeight(4911);
        //titleImageView.setPreserveRatio(true);

        // Create the buttons
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");

        // Add event handlers for buttons
        button1.setOnAction(event -> {
            // Your action for button 1
            System.out.println("Button 1 clicked");
        });

        button1.setOnMouseClicked(this.switchScreen.setScreen(new LoadingScreen(primaryStage)));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, titleImageView, button1);

        StackPane.setAlignment(button1, Pos.CENTER); // Center the button on the image
        double offsetY = imageView.getFitHeight() / 4 + 100;
        StackPane.setMargin(button1, new Insets(offsetY, 0, 0, 0)); // Move button slightly up if needed

        StackPane.setAlignment(titleImageView, Pos.CENTER);
        double offset = imageView.getFitHeight() / 4;
        StackPane.setMargin(titleImageView, new Insets(-offset, 0, 0, 0));

        this.getChildren().add(stackPane);


        this.primaryStage.getScene().setRoot(this);
    }
}
