package com.screens;

import com.controllers.mouse.SwitchScreen;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

//        Image image = new Image("/assets/image/example.png");  // Replace with actual image path
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(300); // Set the image width
//        imageView.setPreserveRatio(true);

        // Create the buttons
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");

        // Add event handlers for buttons
        button1.setOnAction(event -> {
            // Your action for button 1
            System.out.println("Button 1 clicked");
        });

        this.getChildren().add(button1);


        this.primaryStage.getScene().setRoot(this);
    }
}
