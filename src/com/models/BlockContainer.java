/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: This class represents the container that holds building blocks.
 * It organizes blocks in a scrollable grid and interacts with the game board.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.models;

// Represents a coordinate position in the game
import com.commons.Coordinate;
// Represents the game board where blocks can be placed
import com.models.components.Board;
// Represents an individual building block in the game
import com.models.components.BuildingBlock;
// Provides a scrollable container for viewing content
import javafx.scene.control.ScrollPane;
// Used for creating a flexible grid layout for blocks
import javafx.scene.layout.GridPane;
// Defines positioning alignment for layout components
import javafx.geometry.Pos;
// Defines padding and margins for layout components
import javafx.geometry.Insets;
// Used for storing a collection of building blocks
import java.util.Vector;

public class BlockContainer extends Entity {
    private Vector<BuildingBlock> blocks; // List of building blocks in the container

    private final int containerWidth = 346;
    private final int containerHeight = 559;
    private final int blockSpacing = 50;
    private final int blocksPerRow = 2;
    private final int topGap = 10;
    private final int bottomGap = 10;
    private Board board; // Game board reference

    /**
     * Constructor for BlockContainer with position and dimensions.
     * Initializes an empty list of blocks.
     *
     * @param position The position of the container
     * @param width The width of the container
     * @param height The height of the container
     */
    public BlockContainer(Coordinate position, int width, int height) {
        this(new Vector<>(), position, width, height);
    }

    /**
     * Constructor for BlockContainer with a predefined set of blocks.
     * Initializes the container with given blocks and a game board.
     *
     * @param blocks The list of building blocks
     * @param position The position of the container
     * @param width The width of the container
     * @param height The height of the container
     */
    public BlockContainer(Vector<BuildingBlock> blocks, Coordinate position, int width, int height) {
        super(position, true, width, height);
        board = new Board(new Coordinate(355, 160), 600, 400);
        this.blocks = new Vector<>();
        for (BuildingBlock block : blocks) {
            this.blocks.add(block.clone());
        }
        this.draw();
    }

    /**
     * Draws the container UI, placing blocks inside a scrollable grid layout.
     */
    public void draw() {
        this.getChildren().clear(); // clear to avoid duplicate

        board.draw();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(blockSpacing);
        gridPane.setVgap(blockSpacing);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(topGap, 0, bottomGap, 0));

        for (int i = 0; i < blocks.size(); i++) {
            int row = i / blocksPerRow;
            int col = i % blocksPerRow;
            BuildingBlock currentBlock = blocks.get(i);
            currentBlock.setSize(120);
            currentBlock.setLayout(gridPane, this);
            currentBlock.setGridPanePosition(new Coordinate(col, row));
            currentBlock.setBoard(board);
            gridPane.add(currentBlock, col, row);
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setPrefSize(containerWidth, containerHeight);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-background: transparent;" + "-fx-background-color: rgba(217, 217, 217, 0.5);");

        this.getChildren().add(scrollPane); // Adds the scroll pane to the container
        this.getChildren().add(board); // Adds the board to the container
    }

    /**
     * Gets the game board associated with this container.
     *
     * @return The Board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the blocks inside the container and redraws the UI.
     *
     * @param blocks The new set of building blocks
     */
    public void setBlocks(Vector<BuildingBlock> blocks) {
        this.blocks = new Vector<>();
        this.blocks.addAll(blocks);
        draw();
    }

    /**
     * Resets the container by clearing all blocks and redrawing the UI.
     */
    public void reset() {
        blocks.clear();
        draw();
    }
}
