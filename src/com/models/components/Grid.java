/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: Represents the grid and board components of the game.
 * This includes methods for rendering and managing game elements on the board.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.models.components;

// Coordinate class is used for handling positions in the game grid
import com.commons.Coordinate;
// Entity class serves as a base class for all game components that have a position
import com.models.Entity;
// JavaFX Color class for coloring grid cells and objects
import javafx.scene.paint.Color;
// JavaFX Rectangle class to represent individual grid cells visually
import javafx.scene.shape.Rectangle;

/**
 * Represents a grid structure in the game. The grid consists of colored cells that define the game area.
 */
public class Grid extends Entity {
    private Color[][] matrix; // 2D array representing grid cell colors

    /**
     * Constructs a Grid object with a given color matrix and position.
     *
     * @param matrix The 2D color array representing the grid
     * @param position The position of the grid
     * @param width The width of the grid
     * @param height The height of the grid
     */
    public Grid(Color[][] matrix, Coordinate position, double width, double height) {
        super(position, false, width, height);
        this.matrix = matrix;
    }

    /**
     * Draws the grid using JavaFX rectangles, assigning colors based on the matrix.
     */
    public void draw() {
        this.getChildren().clear();

        double cellSize = (double) this.width / this.matrix[0].length;
        for (int row = 0; row < this.matrix.length; row++) {
            for (int col = 0; col < this.matrix[row].length; col++) {
                double cellX = col * cellSize;
                double cellY = row * cellSize;

                Rectangle cell = new Rectangle(cellX, cellY, cellSize, cellSize);
                Color cellColor = this.matrix[row][col] != null ? this.matrix[row][col] : Color.TRANSPARENT;
                cell.setFill(cellColor);

                Color borderColor = this.matrix[row][col] != null ? this.matrix[row][col].darker() : Color.rgb(255,248,163);
                cell.setStroke(borderColor);
                cell.setStrokeWidth(this.matrix[row][col] != null ? cellSize / 10 : cellSize / 20);

                this.getChildren().add(cell);
            }
        }
    }
}
