package com.models.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Vector;

public class BlockContainer extends Pane {
    private Vector<BlockPane> blockPanes;
    private GridPane gridPane;

    private final int containerWidth = 346; // Fixed width
    private final int containerHeight = 559; // Fixed height
    private final int blockSpacing = 20; // Spacing between blocks
    private final int blocksPerRow = 3;

    private final int topGap = 10; // Gap above the first line of blocks
    private final int bottomGap = 10; // Gap below the last line of blocks

    public BlockContainer(Vector<BlockPane> blockPanes) {
        this.blockPanes = blockPanes;

        this.setPrefSize(containerWidth, containerHeight);

        // Create a GridPane for layout
        gridPane = new GridPane(); // Initialize gridPane here
        gridPane.setHgap(blockSpacing); // Horizontal gap between blocks
        gridPane.setVgap(blockSpacing); // Vertical gap between blocks
        gridPane.setAlignment(Pos.CENTER); // Center the blocks in the container

        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0)); // Insets(top, right, bottom, left)

        // Populate the GridPane with block panes
        for (int i = 0; i < blockPanes.size(); i++) {
            int row = i / blocksPerRow; // Calculate row
            int col = i % blocksPerRow; // Calculate column

            gridPane.add(blockPanes.get(i), col, row);
        }

        // Wrap the GridPane in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(containerWidth, containerHeight);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        this.getChildren().add(scrollPane);
    }

    public void addBlockPane(BlockPane newBlockPane) {
        // Add the new block to the blockPanes list
        blockPanes.add(newBlockPane);

        // Calculate row and column for the new block based on the current number of blocks
        int row = (blockPanes.size() - 1) / blocksPerRow; // Calculate row based on the new size
        int col = (blockPanes.size() - 1) % blocksPerRow; // Calculate column based on the new size

        gridPane.add(newBlockPane, col, row);
    }
}
