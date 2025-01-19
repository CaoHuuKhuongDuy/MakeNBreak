package com.controllers.mouse;

import com.commons.Coordinate;
import com.models.Entity;
import com.models.components.Board;
import com.models.components.BuildingBlock;
import com.models.components.Grid;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class DraggingGamePlay implements EventHandler<MouseEvent> {
    private Entity target; // The node being dragged
    private boolean dragging = false; // To distinguish between press and drag
    private double[] offset; // The offset between the mouse and the target's position
    private GridPane gridLayout;
    private Pane mainLayout;
    private Board board;
    private Coordinate gridPanePosition;

    public DraggingGamePlay(Entity target) {
        this.target = target;
    }

    public DraggingGamePlay() {}

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
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {
            Coordinate newPosition = new Coordinate(event.getSceneX() - offset[0], event.getSceneY() - offset[1]);
            target.setPosition(newPosition);
            BuildingBlock block = (BuildingBlock) target;
            Coordinate snapPosition = block.getColoredCellPosition();
            System.out.println("====================================");
            System.out.println("Block position: " + target.getLayoutX() + ", " + target.getLayoutY());
            System.out.println("Snap position: " + snapPosition.x + ", " + snapPosition.y);

        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
//            this.mainLayout.getChildren().remove(target);
//            this.gridLayout.add(target, gridPanePosition.x, gridPanePosition.y);

            // Calculate the block's position relative to the Board
            Bounds blockBounds = target.localToScene(target.getBoundsInLocal());
            Bounds boardBounds = board.localToScene(board.getBoundsInLocal());

            double blockCenterX = blockBounds.getMinX() + blockBounds.getWidth() / 2.0;
            double blockCenterY = blockBounds.getMinY() + blockBounds.getHeight() / 2.0;

            double relativeX = blockCenterX - boardBounds.getMinX();
            double relativeY = blockCenterY - boardBounds.getMinY();

            // Check if block is over the board
            if (relativeX >= 0 && relativeX <= board.getWidth() &&
                    relativeY >= 0 && relativeY <= board.getHeight()) {
                BuildingBlock block = (BuildingBlock) target;
                Coordinate coloredCellPosition = block.getColoredCellPosition();
                Coordinate snapPosition = board.snapToGrid(coloredCellPosition);
                snapPosition = target.getPosition().plus(snapPosition.minus(coloredCellPosition));
                target.setPosition(snapPosition);
            } else {
                this.mainLayout.getChildren().remove(target);
                this.gridLayout.add(target, gridPanePosition.x, gridPanePosition.y);
            }

            dragging = false;
        }

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