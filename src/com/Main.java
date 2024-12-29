package com;

import com.commons.Coordinate;
import com.entities.buildingblocks.BuildingBlock;
import com.entities.buildingblocks.ListBuildingBlock;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Vector;

public class Main extends Application {

    private double offsetX = 0; // Instance fields for offsets
    private double offsetY = 0;

    @Override
    public void start(Stage primaryStage) {
        // Generate building blocks (this could be moved to another method or class as needed)
        Coordinate defaultPosition = new Coordinate(0, 0);
        Coordinate cell00 = new Coordinate(0, 0);
        Coordinate cell01 = new Coordinate(0, 1);
        Coordinate cell02 = new Coordinate(0, 2);
        Coordinate cell10 = new Coordinate(1, 0);
        Coordinate cell11 = new Coordinate(1, 1);
        Coordinate cell12 = new Coordinate(1, 2);
        Coordinate cell20 = new Coordinate(2, 0);
        Coordinate cell21 = new Coordinate(2, 1);
        Coordinate cell22 = new Coordinate(2, 2);

        BuildingBlock buildingBlock1 = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11, cell12)), defaultPosition, Color.RED, true);
        BuildingBlock buildingBlock2 = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell21)), defaultPosition, Color.BLUE, true);
        BuildingBlock buildingBlock3 = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell01, cell10, cell11)), defaultPosition, Color.GREEN, true);
        BuildingBlock buildingBlock4 = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11)), defaultPosition, Color.PINK, true);
        BuildingBlock buildingBlock5 = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11, cell12, cell21)), defaultPosition, Color.PURPLE, true);

        ListBuildingBlock listBuildingBlock = new ListBuildingBlock(new Vector<>(Arrays.asList(buildingBlock1, buildingBlock2, buildingBlock3, buildingBlock4, buildingBlock5)));

        // Create a GridPane to hold the colored rectangles representing the building
        GridPane buildingGrid = new GridPane();
        buildingGrid.setVgap(1);  // Vertical gap between cells
        buildingGrid.setHgap(1);  // Horizontal gap between cells
        buildingGrid.setTranslateX(250);  // X position offset
        buildingGrid.setTranslateY(200);
        // listener for mouse pressed event
        buildingGrid.setOnMousePressed(event -> {
            Color[][] building = listBuildingBlock.generateBuilding(20, 20, 10);
            // Populate the grid with rectangles based on the building's color matrix
            // clear building grid
            buildingGrid.getChildren().clear();
            for (int row = 0; row < building.length; row++) {
                for (int col = 0; col < building[row].length; col++) {
                    Color color = building[row][col];

                    // Create a rectangle for each cell and set its color
                    Rectangle rectangle = new Rectangle(30, 30, color);  // 30x30 size for each cell
                    buildingGrid.add(rectangle, col, row);  // Add rectangle to grid at (col, row)
                }
            }
        });

        // Create a root container (StackPane) and add the building grid and rectangle to it
        StackPane root = new StackPane();
        root.getChildren().addAll(buildingGrid);  // Add both grid and character to the same root

        // Set up the scene and the stage
        Scene scene = new Scene(root, 800, 600);  // Scene size 800x600
        primaryStage.setTitle("Drag the Rectangle and View Building");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
