package com.models.components;

import com.commons.Coordinate;
import com.models.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid extends Entity {
    private Color[][] matrix;

    public Grid(Color[][] matrix, Coordinate position, double width, double height) {
        super(position, false, width, height);
        this.matrix = matrix;
    }

    public void draw() {
        this.getChildren().clear();

        double cellSize = (double) this.width / this.matrix[0].length;
        for (int row = 0; row < this.matrix.length; row++) {
            for (int col = 0; col < this.matrix[row].length; col++) {
                // Calculate the position of each cell
                double cellX = col * cellSize;
                double cellY = row * cellSize;

                // Create a new rectangle (cell)
                Rectangle cell = new Rectangle(cellX, cellY, cellSize, cellSize);

                // Set the fill color based on the matrix value, defaulting to transparent if null
                Color cellColor = this.matrix[row][col] != null ? this.matrix[row][col] : Color.TRANSPARENT;
                cell.setFill(cellColor);

                // Optionally, add a border to each cell
                Color color = this.matrix[row][col] != null ? this.matrix[row][col].darker() : Color.rgb(255,248,163);
                cell.setStroke(color); // Border color
                double borderWith = this.matrix[row][col] != null ? cellSize / 10 : cellSize / 20;
                cell.setStrokeWidth(borderWith);     // Border width

                // Add the cell to the grid's children
                this.getChildren().add(cell);
            }
        }
    }


}
