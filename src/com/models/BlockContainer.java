package com.models;

import com.commons.Coordinate;
import com.models.components.Board;
import com.models.components.BuildingBlock;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


import java.util.Vector;

public class BlockContainer extends Entity {
    private Vector<BuildingBlock> blocks;

    private final int containerWidth = 346; // Fixed width
    private final int containerHeight = 559; // Fixed height
    private final int blockSpacing = 50; // Spacing between blocks
    private final int blocksPerRow = 2;
    private final int topGap = 30; // Gap above the first line of blocks
    private final int bottomGap = 30; // Gap below the last line of blocks

    public BlockContainer(Coordinate position, int width, int height) {
        this(new Vector<>(), position, width, height);
    }

    public BlockContainer(Vector<BuildingBlock> blocks, Coordinate position, int width, int height) {
        super(position, true, width, height);

        this.blocks = blocks;
        this.draw();
    }

    public void draw() {
        this.getChildren().clear(); // Clear to avoid duplicates

        GridPane gridPane = new GridPane();
        gridPane.setHgap(blockSpacing);
        gridPane.setVgap(blockSpacing);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0));

        for (int i = 0; i < blocks.size(); i++) {
            int row = i / blocksPerRow;
            int col = i % blocksPerRow;

            BuildingBlock currentBlock = blocks.get(i);
            currentBlock.setSize(77);
            currentBlock.setLayout(gridPane, this);
            gridPane.add(currentBlock, col, row);
//            currentBlock.setGridPanePosition(new Coordinate(col, row));
//            currentBlock.setPosition(new Coordinate(col * (blockSpacing), row * (blockSpacing)));
//            this.getChildren().add(currentBlock);
        }

        // Add GridPane to the ScrollPane
//        ScrollPane scrollPane = new ScrollPane(gridPane);
//        scrollPane.setPrefSize(this.width, this.height);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setPannable(true); // Enable panning
//        this.setClip(null); // Disable clipping
//        gridPane.setClip(null); // Ensure no clipping in GridPane
//        scrollPane.setStyle("-fx-background: transparent;" + "-fx-background-color: rgba(217, 217, 217, 0.5);");

        Board board = new Board(new Coordinate(180, 80), 600, 400);
        board.draw();

        this.getChildren().addAll(gridPane, board);
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
