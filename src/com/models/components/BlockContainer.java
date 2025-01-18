package com.models.components;

import com.commons.Coordinate;
import com.models.Entity;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


import java.util.Vector;

public class BlockContainer extends Entity {
    private Vector<BuildingBlock> blocks;

    private final int containerWidth = 346; // Fixed width
    private final int containerHeight = 559; // Fixed height
    private final int blockSpacing = 20; // Spacing between blocks
    private final int blocksPerRow = 3;
    private final int topGap = 10; // Gap above the first line of blocks
    private final int bottomGap = 10; // Gap below the last line of blocks

    public BlockContainer(Coordinate position, int width, int height) {
        this(new Vector<>(), position, width, height);
    }

    public BlockContainer(Vector<BuildingBlock> blocks, Coordinate position, int width, int height) {
        super(position, true, width, height);

        this.blocks = blocks;
        this.draw();
    }

//    public void draw() {
//        this.getChildren().clear(); // Clear to avoid duplicates
//
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(blockSpacing);
//        gridPane.setVgap(blockSpacing);
//        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0));
//
//        for (int i = 0; i < blocks.size(); i++) {
//            int row = i / blocksPerRow;
//            int col = i % blocksPerRow;
//
//            BuildingBlock currentBlock = blocks.get(i);
//            currentBlock.setSize(50);
//            currentBlock.draw();
//
//            // Add dragging functionality to each block
//            currentBlock.setOnMousePressed(event -> {
//                // Store the initial click position
//                currentBlock.setUserData(new double[] { event.getSceneX(), event.getSceneY() });
//            });
//
//            currentBlock.setOnMouseDragged(event -> {
//                // Retrieve the initial position
//                double[] startPos = (double[]) currentBlock.getUserData();
//                double deltaX = event.getSceneX() - startPos[0];
//                double deltaY = event.getSceneY() - startPos[1];
//
//                // Move the block
//                currentBlock.setTranslateX(currentBlock.getTranslateX() + deltaX);
//                currentBlock.setTranslateY(currentBlock.getTranslateY() + deltaY);
//
//                // Update the starting position
//                currentBlock.setUserData(new double[] { event.getSceneX(), event.getSceneY() });
//            });
//
//            // Add the block to the GridPane
//            gridPane.add(blocks.get(i), col, row);
//        }
//
//        ScrollPane scrollPane = new ScrollPane(gridPane);
//        scrollPane.setPrefSize(this.width, this.height);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setPannable(true);
//        scrollPane.setStyle("-fx-background: transparent;" + "-fx-background-color: rgba(217, 217, 217, 0.5);");
//        this.getChildren().add(scrollPane);
//    }

    public void draw() {
        this.getChildren().clear(); // Clear to avoid duplicates

        Pane mainPane = new Pane(); // Top-level container for ScrollPane and draggable blocks

        // Create the GridPane for the blocks inside the ScrollPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(blockSpacing);
        gridPane.setVgap(blockSpacing);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0));

        // Add blocks to the GridPane
        for (int i = 0; i < blocks.size(); i++) {
            int row = i / blocksPerRow;
            int col = i % blocksPerRow;

            BuildingBlock currentBlock = blocks.get(i); // Declare currentBlock for better readability

            currentBlock.setSize(77);
            currentBlock.draw();
            gridPane.add(currentBlock, col, row);

            // Add drag functionality to the currentBlock
            currentBlock.setOnMousePressed(event -> {
                // Bring the current block to the front for dragging
                mainPane.getChildren().add(currentBlock); // Move block to the mainPane
                currentBlock.toFront(); // Ensure it appears above other elements

                // Store the starting mouse position
                currentBlock.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
            });

            currentBlock.setOnMouseDragged(event -> {
                // Retrieve starting mouse position
                double[] startPos = (double[]) currentBlock.getUserData();
                double deltaX = event.getSceneX() - startPos[0];
                double deltaY = event.getSceneY() - startPos[1];

                // Update the block's position
                currentBlock.setTranslateX(currentBlock.getTranslateX() + deltaX);
                currentBlock.setTranslateY(currentBlock.getTranslateY() + deltaY);

                // Update starting position
                currentBlock.setUserData(new double[]{event.getSceneX(), event.getSceneY()});
            });

            currentBlock.setOnMouseReleased(event -> {
                // Calculate the final grid position
                double blockX = currentBlock.getTranslateX();
                double blockY = currentBlock.getTranslateY();

                // Calculate the closest grid cell position based on block size and spacing
                int newRow = (int) Math.round((blockY + currentBlock.getHeight() / 2) / (currentBlock.getHeight() + blockSpacing));
                int newCol = (int) Math.round((blockX + currentBlock.getWidth() / 2) / (currentBlock.getWidth() + blockSpacing));

                // Snap the block to the calculated grid cell position
                currentBlock.setTranslateX(newCol * (currentBlock.getWidth() + blockSpacing));
                currentBlock.setTranslateY(newRow * (currentBlock.getHeight() + blockSpacing));

                // Optionally, you can update the underlying data structure (e.g., block list)
                // blocks.set(i, currentBlock);  // Reassign the block's position in the list if needed
            });
        }

        // Add GridPane to the ScrollPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(this.width, this.height);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true); // Enable panning
        scrollPane.setStyle("-fx-background: transparent;" + "-fx-background-color: rgba(217, 217, 217, 0.5);");

        // Add ScrollPane and blocks to the main pane
        mainPane.getChildren().add(scrollPane);

        // Add mainPane to the scene graph
        this.getChildren().add(mainPane);
    }


    public void setBlocks(Vector<BuildingBlock> blocks) {
        this.blocks = blocks;
        draw();
    }

    public void addBlockPane(BuildingBlock newBlockPane) {
        blocks.add(newBlockPane);
        draw();
    }
}
