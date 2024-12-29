package com;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private double offsetX = 0; // Instance fields for offsets
    private double offsetY = 0;

    @Override
    public void start(Stage primaryStage) {
        // Create a rectangle (your character entity)
        Rectangle character = new Rectangle(100, 100, 100, 50);  // width, height, and initial position
        character.setFill(Color.BLUE);

        // Handle mouse pressed event to record the mouse offset from the rectangle's position
        character.setOnMousePressed(event -> {
            System.out.println("Mouse pressed");
            offsetX = event.getSceneX() - character.getTranslateX();
            offsetY = event.getSceneY() - character.getTranslateY();
        });

        // Handle mouse dragged event to update the position of the rectangle
        character.setOnMouseDragged(event -> {
            System.out.println("Mouse dragged");
            // Set the new position based on the mouse movement
            character.setTranslateX(event.getSceneX() - offsetX);
            character.setTranslateY(event.getSceneY() - offsetY);
        });

        // Create a root container (StackPane) and add the rectangle to it
        StackPane root = new StackPane();
        root.getChildren().add(character);

        // Set up the scene and the stage
        Scene scene = new Scene(root, 800, 600);  // Scene size 800x600
        primaryStage.setTitle("Drag the Rectangle!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
