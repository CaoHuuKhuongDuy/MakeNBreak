/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a building block in the game. Manages the block's cells, position, color,
 * and interactivity. Provides functionality for drawing, rotating, flipping, and dragging the block.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models.components;

import com.commons.Coordinate; // Represents a coordinate in the game grid.
import com.controllers.mouse.DraggingGamePlay; // Handles dragging functionality for the block.
import com.models.Entity; // Base class for game entities.

import java.util.Vector; // A dynamic array implementation.

import javafx.scene.input.KeyEvent; // Represents a key event in JavaFX.
import javafx.scene.layout.GridPane; // A layout container for arranging nodes in a grid.
import javafx.scene.layout.Pane; // A base class for layout containers.
import javafx.scene.paint.Color; // Represents a color in the JavaFX color space.
import javafx.scene.shape.Rectangle; // Represents a rectangle shape in JavaFX.

public class BuildingBlock extends Entity {
    private Vector<Coordinate> cells; // The cells that make up the block.
    private DraggingGamePlay draggingGamePlayController; // Handles dragging functionality.
    private Color color; // The color of the block.
    private static final int MAX_SIZE = 3; // The maximum size of the block.
    private int cellSpacing = 0; // The spacing between cells in the block.

    /**
     * Constructs a BuildingBlock with specified cells, position, color, and interactability.
     *
     * @param cells        The cells that make up the block.
     * @param position     The position of the block.
     * @param color        The color of the block.
     * @param interactable Whether the block is interactable.
     */
    public BuildingBlock(Vector<Coordinate> cells, Coordinate position, Color color, boolean interactable) {
        super(position, interactable);
        this.cells = cells;
        this.color = color;
        this.initController();
    }

    /**
     * Constructs a BuildingBlock with specified cells and default position, color, and interactability.
     *
     * @param cells The cells that make up the block.
     */
    public BuildingBlock(Vector<Coordinate> cells) {
        super(new Coordinate(0, 0), true);
        this.color = Color.TRANSPARENT;
        this.cells = cells;
        this.initController();
    }

    /**
     * Initializes the dragging controller for the block.
     */
    private void initController() {
        this.draggingGamePlayController = new DraggingGamePlay(this);
        this.setOnMousePressed(this.draggingGamePlayController);
        this.setOnMouseDragged(this.draggingGamePlayController);
        this.setOnMouseReleased(this.draggingGamePlayController);
        this.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (this.draggingGamePlayController.isDragging()) {
                if (keyEvent.getCode().toString().equals("F") || keyEvent.getCode().toString().equals("f")) {
                    this.flip();
                }
                if (keyEvent.getCode().toString().equals("R") || keyEvent.getCode().toString().equals("r")) {
                    this.rotate();
                }
            }
        });
    }

    public DraggingGamePlay getDraggingGamePlayController() {
        return this.draggingGamePlayController;
    }

    public void setGridPanePosition(Coordinate gridPanePosition) {
        this.draggingGamePlayController.setGridPanePosition(gridPanePosition);
    }

    public void setSize(double size) {
        super.setSize(size, size);
    }

    public void setPosition(Coordinate position) {
        super.setPosition(position);
    }

    public void setColor(Color color) {
        this.color = color;
        this.draw();
    }

    /**
     * Draws the block by rendering its cells as rectangles.
     */
    public void draw() {
        this.getChildren().clear();
        int maxX = -1, maxY = -1;
        for (Coordinate cell : this.getCells()) {
            maxX = Math.max(maxX, cell.x);
            maxY = Math.max(maxY, cell.y);
        }
        for (Coordinate cell : this.getCells()) {
            cell.x += MAX_SIZE - maxX - 1;
            cell.y += MAX_SIZE - maxY - 1;
            Rectangle cellRect = new Rectangle(this.width / 3.0, this.height / 3.0);
            cellRect.setFill(this.getColor());
            cellRect.setStroke(Color.BLACK);
            cellRect.setStrokeWidth(1);

            cellRect.setX(cell.y * (this.width / 3.0 + cellSpacing));
            cellRect.setY(cell.x * (this.height / 3.0 + cellSpacing));
            this.getChildren().add(cellRect);
        }
    }

    public Coordinate getColoredCell() {
        Coordinate position = new Coordinate(Integer.MAX_VALUE, Integer.MAX_VALUE);
        for (Coordinate cell : this.cells) {
            if (cell.x < position.x) {
                position = cell;
            }
            else if (cell.x == position.x && cell.y < position.y) position = cell;
        }
        return position;
    }

    public Coordinate getCellPosition(Coordinate cell) {
        return new Coordinate(this.position.x + cell.y * (this.width / 3.0 + cellSpacing), this.position.y + cell.x * (this.height / 3.0 + cellSpacing));
    }

    public Vector<Coordinate> getCells() {
        return this.cells;
    }

    public Color getColor() {
        return this.color;
    }

    public BuildingBlock clone() {
        Vector<Coordinate> newCells = new Vector<>();
        for (Coordinate cell : this.cells) {
            newCells.add(new Coordinate(cell.x, cell.y));
        }
        return new BuildingBlock(newCells, new Coordinate(this.position.x, this.position.y), this.color, this.interactable);
    }

    /**
     * Rotates the block 90 degrees clockwise.
     */
    public void rotate() {
        this.cells.replaceAll(coordinate -> new Coordinate(coordinate.y, MAX_SIZE - 1 - coordinate.x));
        this.draw();
    }

    /**
     * Flips the block horizontally.
     */
    public void flip() {
        this.cells.replaceAll(coordinate -> new Coordinate(coordinate.x, MAX_SIZE - 1 - coordinate.y));
        this.draw();
    }

    public BuildingBlock setLayout(GridPane gridPane, Pane mainLayout) {
        this.draggingGamePlayController.setLayout(gridPane, mainLayout);
        return this;
    }

    public BuildingBlock setBoard(Board board) {
        this.draggingGamePlayController.setBoard(board);
        return this;
    }
}