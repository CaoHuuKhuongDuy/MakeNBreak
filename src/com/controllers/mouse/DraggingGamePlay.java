package com.controllers.mouse;

import com.commons.Coordinate;
import com.models.Entity;
import com.models.components.Board;
import com.models.components.BuildingBlock;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;


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

    public DraggingGamePlay(Entity target) {
        this.target = target;
        this.cellsInBoard = null;
    }

    public DraggingGamePlay() {
        this.cellsInBoard = null;
    }

    public void setCellsInBoard(Vector<Coordinate> cellsInBoard) {
        this.cellsInBoard = cellsInBoard;
    }

    public DraggingGamePlay setTarget(Entity target) {
        this.target = target;
        return this;
    }

    public void setGridPanePosition(Coordinate gridPanePosition) {
        this.gridPanePosition = gridPanePosition;
    }

    public DraggingGamePlay setGridLayout(GridPane gridLayout) {
        this.gridLayout = gridLayout;
        return this;
    }

    public DraggingGamePlay setBoard(Board board) {
        this.board = board;
        return this;
    }

    public DraggingGamePlay setMainLayout(Pane setMainLayout) {
        this.mainLayout = setMainLayout;
        return this;
    }

    public DraggingGamePlay setLayout(GridPane gridLayout, Pane mainLayout) {
        this.setGridLayout(gridLayout).setMainLayout(mainLayout);
        return this;
    }

    public boolean isDragging() {
        return dragging;
    }

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
            Coordinate newPosition = new Coordinate(event.getSceneX() - offset[0], event.getSceneY() - offset[1]);
            target.setPosition(newPosition);
            target.requestFocus();
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
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

            // Check if block is over the board
            if (relativeX >= 0 && relativeX <= board.getWidth() &&
                    relativeY >= 0 && relativeY <= board.getHeight()) {
                Coordinate snapPosition = board.snapToGrid(coloredCellPosition, block);
                if (snapPosition != null) {
                    snapPosition = target.getPosition().plus(snapPosition.minus(coloredCellPosition));
                    target.setPosition(snapPosition);
                    return;
                }
            }
            // If the block is not over the board or can't snap, reset its position
            this.clearBoard();
            this.mainLayout.getChildren().remove(target);
            this.gridLayout.add(target, gridPanePosition.x, gridPanePosition.y);
            dragging = false;
        }


    }

    private void clearBoard() {
        if (cellsInBoard != null) {
            for (Coordinate cell : cellsInBoard) {
                board.setOccupied(cell.x, cell.y, Color.TRANSPARENT);
            }
        }
        this.setCellsInBoard(null);
    }

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