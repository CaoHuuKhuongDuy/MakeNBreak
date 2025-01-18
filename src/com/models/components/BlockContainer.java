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

        this.draw();
    }

    public void draw() {

        this.getChildren().clear(); // clear to avoid duplicate

        gridPane = new GridPane();
        gridPane.setHgap(blockSpacing);
        gridPane.setVgap(blockSpacing);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0));

        for (int i = 0; i < blockPanes.size(); i++) {
            int row = i / blocksPerRow;
            int col = i % blocksPerRow;

            gridPane.add(blockPanes.get(i), col, row);
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(containerWidth, containerHeight);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        this.getChildren().add(scrollPane);
    }

    public void addBlockPane(BlockPane newBlockPane) {
        blockPanes.add(newBlockPane);
        draw();
    }
}
