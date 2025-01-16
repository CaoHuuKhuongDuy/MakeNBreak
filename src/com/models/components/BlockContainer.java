package com.models.components;

import com.commons.Coordinate;
import com.screens.GameScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Vector;

public class BlockContainer extends Pane {
    private Vector<BuildingBlock> buildingBlocks;

    private final int containerWidth = 346; // Fixed width
    private final int containerHeight = 559; // Fixed height
    private final int blockWidth = 60; // Block width
    private final int blockHeight = 60; // Block height
    private final int blockSpacing = 20; // Spacing between blocks
    private final int cellSpacing = 0; // Spacing between cells within a block
    private final int blocksPerRow = 3;

    private final int topGap = 10; // Gap above the first line of blocks
    private final int bottomGap = 10; // Gap below the last line of blocks

    public BlockContainer(Vector<BuildingBlock> buildingBlocks) {
        this.buildingBlocks = buildingBlocks;

        // Set fixed size for the container
        this.setPrefSize(containerWidth, containerHeight);

        // Create a GridPane for layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(blockSpacing); // Horizontal gap between blocks
        gridPane.setVgap(blockSpacing); // Vertical gap between blocks
        gridPane.setAlignment(Pos.CENTER); // Center the blocks in the container

        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0)); // Insets(top, right, bottom, left)


        // Populate the GridPane with building blocks
        for (int i = 0; i < buildingBlocks.size(); i++) {
            int row = i / blocksPerRow; // Calculate row
            int col = i % blocksPerRow; // Calculate column

            // Render the block and add to the grid
            Pane blockPane = renderBlock(buildingBlocks.get(i));
            gridPane.add(blockPane, col, row);
        }

        // Wrap the GridPane in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(containerWidth, containerHeight);
        scrollPane.setFitToWidth(true); // Ensure it fits the container width
        scrollPane.setContent(gridPane);
        scrollPane.setPannable(true); // Allow scrolling
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Add ScrollPane to the BlockContainer
        this.getChildren().addAll(scrollPane);
    }

    // Method to render a single block as a Pane
    private Pane renderBlock(BuildingBlock block) {
        Pane blockPane = new Pane();

        // Render the block cells
        for (Coordinate cell : block.getCells()) {
            Rectangle cellRect = new Rectangle(blockWidth / 2.0, blockHeight / 2.0);
            cellRect.setFill(block.getColor());
            cellRect.setStroke(Color.BLACK);
            cellRect.setStrokeWidth(1);

            // Position the cell with the defined cell spacing
            cellRect.setX(cell.y * (blockWidth / 2.0 + cellSpacing));
            cellRect.setY(cell.x * (blockHeight / 2.0 + cellSpacing));
            blockPane.getChildren().add(cellRect);
        }

        // Enable dragging for the block
        blockPane.setOnMousePressed(event -> {
            blockPane.setMouseTransparent(true); // Avoid triggering other events
            blockPane.toFront(); // Bring the block to the front
        });

        blockPane.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - blockPane.getBoundsInParent().getWidth() / 2;
            double newY = event.getSceneY() - blockPane.getBoundsInParent().getHeight() / 2;

            blockPane.setLayoutX(newX);
            blockPane.setLayoutY(newY);
        });

        blockPane.setOnMouseReleased(event -> {
            blockPane.setMouseTransparent(false); // Re-enable interactions
            blockPane.setLayoutX(event.getSceneX() - blockPane.getBoundsInParent().getWidth() / 2);
            blockPane.setLayoutY(event.getSceneY() - blockPane.getBoundsInParent().getHeight() / 2);

            Pane parentPane = (Pane) this.getParent();
            if (parentPane instanceof GameScreen) {
                GameScreen gameScreen = (GameScreen) parentPane;
                ((Pane) blockPane.getParent()).getChildren().remove(blockPane);
                gameScreen.getChildren().add(blockPane);
            }
        });
        return blockPane;
    }

}