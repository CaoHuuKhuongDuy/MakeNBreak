/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: Handles drag-and-drop interactions for building blocks in the Make 'n' Break game.
 * This class manages the movement of game pieces across the board, detects valid placement,
 * and ensures smooth interactions with the game UI.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.controllers.mouse;

// Represents a coordinate point in the game
import com.commons.Coordinate;
// Represents a generic game entity that can be interacted with
import com.models.Entity;
// Represents the game board where blocks are placed
import com.models.components.Board;
// Represents individual building blocks used in the game
import com.models.components.BuildingBlock;
// Handles mouse events for detecting user interactions
import javafx.event.EventHandler;
// Represents bounding boxes for UI elements
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
// Represents a JavaFX node (UI component)
import javafx.scene.Node;
// Allows scrolling of game components
import javafx.scene.control.ScrollPane;
// Represents a grid layout in JavaFX, used for positioning elements
import javafx.scene.layout.GridPane;
// Handles mouse event detection
import javafx.scene.input.MouseEvent;
// Represents a basic layout container
import javafx.scene.layout.Pane;
// Represents colors used for blocks and board interactions
import javafx.scene.paint.Color;

import java.util.Vector;

public class DraggingGamePlay implements EventHandler<MouseEvent> {
    private Entity target; // The node being dragged
    private boolean dragging = false; // To distinguish between press and drag
    private double[] offset; // The offset between the mouse and the target's position
    private GridPane gridLayout;
    private Pane mainLayout;
    private Board board;
    private Coordinate gridPanePosition;
    private Vector<Coordinate> cellsInBoard;

    /**
     * Constructor for DraggingGamePlay
     * @param target The entity that is being dragged
     */
    public DraggingGamePlay(Entity target) {
        this.target = target;
        this.cellsInBoard = null;
    }

    /**
     * Sets the occupied cells in the board
     * @param cellsInBoard Vector of coordinates representing occupied cells
     */
    public void setCellsInBoard(Vector<Coordinate> cellsInBoard) {
        this.cellsInBoard = cellsInBoard;
    }

    /**
     * Sets the position of the grid pane
     * @param gridPanePosition Coordinate representing the position
     */
    public void setGridPanePosition(Coordinate gridPanePosition) {
        this.gridPanePosition = gridPanePosition;
    }

    /**
     * Sets the grid layout used for organizing UI components
     */
    public DraggingGamePlay setGridLayout(GridPane gridLayout) {
        this.gridLayout = gridLayout;
        return this;
    }

    /**
     * Sets the board where the building blocks will be placed
     */
    public DraggingGamePlay setBoard(Board board) {
        this.board = board;
        return this;
    }

    /**
     * Sets the main layout that contains UI elements
     */
    public DraggingGamePlay setMainLayout(Pane setMainLayout) {
        this.mainLayout = setMainLayout;
        return this;
    }

    /**
     * Configures both grid and main layout
     */
    public DraggingGamePlay setLayout(GridPane gridLayout, Pane mainLayout) {
        this.setGridLayout(gridLayout).setMainLayout(mainLayout);
        return this;
    }

    /**
     * Checks if an entity is currently being dragged
     * @return true if dragging, false otherwise
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Handles mouse events: pressing, dragging, and releasing
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            // Store the offset between the mouse and the target's position
            offset = new double[]{event.getSceneX() - target.getLayoutX(), event.getSceneY() - target.getLayoutY()};
            dragging = true;
            ScrollPane scrollPane = findScrollPane(target);
            if (scrollPane != null) {
                double hOffset = scrollPane.getHvalue() * (scrollPane.getContent().getBoundsInLocal().getWidth() - scrollPane.getViewportBounds().getWidth());
                double vOffset = scrollPane.getVvalue() * (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());
                offset[0] += hOffset;
                offset[1] += vOffset;

                // Move the block to the destination layout for dragging
                gridLayout.getChildren().remove(target);
                mainLayout.getChildren().add(target);
            }
            this.clearBoard();
            this.target.toFront();
            target.requestFocus();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {
            // Update the block's position based on the mouse movement
            Coordinate newPosition = new Coordinate(event.getSceneX() - offset[0], event.getSceneY() - offset[1]);
            target.setPosition(newPosition);
            target.requestFocus();
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            // Handles snapping the block back to the grid or resetting its position
            BuildingBlock block = (BuildingBlock) target;
            Coordinate coloredCell = block.getColoredCell();
            Coordinate coloredCellPosition = block.getCellPosition(coloredCell);

            Bounds tmpBound = block.localToScene(block.getBoundsInLocal());
            Bounds blockBounds = new BoundingBox(tmpBound.getMinX() + coloredCell.y * 40, tmpBound.getMinY() + coloredCell.x * 40, 40, 40);

            Bounds boardBounds = board.localToScene(board.getBoundsInLocal());

            double blockCenterX = blockBounds.getMinX() + blockBounds.getWidth() / 2.0;
            double blockCenterY = blockBounds.getMinY() + blockBounds.getHeight() / 2.0;

            double relativeX = blockCenterX - boardBounds.getMinX();
            double relativeY = blockCenterY - boardBounds.getMinY();

            if (relativeX >= 0 && relativeX <= board.getWidth() &&
                    relativeY >= 0 && relativeY <= board.getHeight()) {
                Coordinate snapPosition = board.snapToGrid(coloredCellPosition, block);
                if (snapPosition != null) {
                    snapPosition = target.getPosition().plus(snapPosition.minus(coloredCellPosition));
                    target.setPosition(snapPosition);
                    return;
                }
            }
            this.clearBoard();
            this.mainLayout.getChildren().remove(target);
            this.gridLayout.add(target, gridPanePosition.x, gridPanePosition.y);
            dragging = false;
        }
    }

    /**
     * Clears the board by resetting occupied cells
     */
    private void clearBoard() {
        if (cellsInBoard != null) {
            for (Coordinate cell : cellsInBoard) {
                board.setOccupied(cell.x, cell.y, Color.TRANSPARENT);
            }
        }
        this.setCellsInBoard(null);
    }

    /**
     * Finds the ScrollPane ancestor of a given node
     */
    private ScrollPane findScrollPane(Node node) {
        Node current = node;
        while (current != null) {
            if (current instanceof ScrollPane) {
                return (ScrollPane) current;
            }
            current = current.getParent();
        }
        return null;
    }
}
