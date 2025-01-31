/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: This class represents the game board for the application.
 * It manages the grid where building blocks are placed, ensuring valid positioning.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.models.components;

// Import necessary classes for coordinates, global variables, and entity structure
import com.commons.Coordinate; // Represents a 2D coordinate system used for block placement
import com.commons.Globals; // Stores global game resources like images and settings
import com.models.Entity; // Base class for all drawable entities in the game
import javafx.scene.image.ImageView; // Used to render the board image
import javafx.scene.paint.Color; // Manages colors for occupied cells on the board
import java.util.Vector; // Stores coordinates of cells occupied by a placed block

/**
 * Represents the game board where players place building blocks.
 * The board is a 2D grid where each cell tracks whether it is occupied.
 */
public class Board extends Entity {

    private Color[][] occupied; // Tracks occupied cells and their colors
    private Coordinate[][] cells; // Stores the positions of each grid cell
    private final int DEFAULT_ROW = 10, DEFAULT_COL = 15; // Default board size

    /**
     * Constructs a Board object with a grid of specified width and height.
     * Initializes the occupied cells and sets up the grid layout.
     *
     * @param position The top-left position of the board.
     * @param width    The width of the board.
     * @param height   The height of the board.
     */
    public Board(Coordinate position, int width, int height) {
        super(position, false, width, height);
        this.occupied = new Color[DEFAULT_ROW][DEFAULT_COL];
        for (int i = 0; i < DEFAULT_ROW; i++) {
            for (int j = 0; j < DEFAULT_COL; j++) {
                this.occupied[i][j] = Color.TRANSPARENT;
            }
        }

        this.cells = new Coordinate[DEFAULT_ROW][DEFAULT_COL];
        Coordinate startCenter = new Coordinate(20, 20);
        for (int i = 0; i < DEFAULT_ROW; i++) {
            for (int j = 0; j < DEFAULT_COL; j++) {
                this.cells[i][j] = startCenter.plus(new Coordinate(j * 40, i * 40));
            }
        }
    }

    /**
     * Resets the board by clearing all occupied cells.
     */
    public void reset() {
        for (int i = 0; i < DEFAULT_ROW; i++) {
            for (int j = 0; j < DEFAULT_COL; j++) {
                this.occupied[i][j] = Color.TRANSPARENT;
            }
        }
    }

    /**
     * Returns the 2D array representing occupied cells on the board.
     *
     * @return The color matrix of occupied cells.
     */
    public Color[][] getOccupied() {
        return this.occupied;
    }

    /**
     * Checks if a specific cell on the board is available for placement.
     *
     * @param x Row index.
     * @param y Column index.
     * @return True if the cell is free, otherwise false.
     */
    private boolean canPlace(int x, int y) {
        return x >= 0 && x < DEFAULT_ROW && y >= 0 && y < DEFAULT_COL && this.occupied[x][y] == Color.TRANSPARENT;
    }

    /**
     * Determines if a building block can be placed at a specific board position.
     *
     * @param block The building block to be placed.
     * @param root  The target position for the block.
     * @return True if placement is valid, otherwise false.
     */
    private boolean canPlaceBlock(BuildingBlock block, Coordinate root) {
        Coordinate firstColoredCell = block.getColoredCell();
        Coordinate diff = root.minus(firstColoredCell);
        for (Coordinate cell : block.getCells()) {
            int x = diff.x + cell.x;
            int y = diff.y + cell.y;
            if (!canPlace(x, y)) return false;
        }
        return true;
    }

    /**
     * Marks a specific cell as occupied by a given color.
     *
     * @param x     Row index.
     * @param y     Column index.
     * @param color Color to mark the cell with.
     */
    public void setOccupied(int x, int y, Color color) {
        if (x < 0 || x >= DEFAULT_ROW || y < 0 || y >= DEFAULT_COL) return;
        this.occupied[x][y] = color;
    }

    /**
     * Adjusts a given position to the nearest grid cell and places a block if valid.
     *
     * @param position The position to snap.
     * @param block    The building block being placed.
     * @return Adjusted coordinate if placement is valid, otherwise null.
     */
    public Coordinate snapToGrid(Coordinate position, BuildingBlock block) {
        position = position.minus(this.position);
        position = position.plus(new Coordinate(20, 20));

        Coordinate closetCellPosition = new Coordinate(0, 0);
        Coordinate closetCell = new Coordinate(0, 0);
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < DEFAULT_ROW; i++) {
            for (int j = 0; j < DEFAULT_COL; j++) {
                double distance = position.distance(this.cells[i][j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closetCellPosition = this.cells[i][j];
                    closetCell = new Coordinate(i, j);
                }
            }
        }
        if (minDistance == Double.MAX_VALUE) return null;
        if (!canPlaceBlock(block, closetCell)) return null;

        Coordinate firstColoredCell = block.getColoredCell();
        Coordinate diff = closetCell.minus(firstColoredCell);
        Vector<Coordinate> cellsInBoard = new Vector<>();
        for (Coordinate cell : block.getCells()) {
            int x = diff.x + cell.x;
            int y = diff.y + cell.y;
            this.occupied[x][y] = block.getColor();
            cellsInBoard.add(new Coordinate(x, y));
        }
        block.getDraggingGamePlayController().setCellsInBoard(cellsInBoard);
        closetCellPosition = closetCellPosition.minus(new Coordinate(20, 20));
        return closetCellPosition.plus(this.position);
    }

    /**
     * Renders the game board by adding an image representation.
     */
    @Override
    public void draw() {
        this.getChildren().clear();
        ImageView playBoard = new ImageView(Globals.getResource("/resources/assets/images/board.png"));
        this.getChildren().add(playBoard);
    }
}
