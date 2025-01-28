package com.models.components;

import com.commons.Coordinate;
import com.controllers.mouse.DraggingGamePlay;
import com.models.Entity;

import java.util.Vector;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class BuildingBlock extends Entity {
    private Vector<Coordinate > cells;
    private DraggingGamePlay draggingGamePlayController;
    private Color color;
    private static final int MAX_SIZE = 3;
    private int cellSpacing = 0;

    public BuildingBlock(Vector<Coordinate> cells, Coordinate position, Color color, boolean interactable) {
        super(position, interactable);
        this.cells = cells;
        this.color = color;
        this.initController();
    }

    public BuildingBlock(Vector<Coordinate> cells) {
        super(new Coordinate(0, 0), true);
        this.color = Color.TRANSPARENT;
        this.cells = cells;
        this.initController();
    }

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

    public Vector <Coordinate> getCells() {
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

    public void rotate() {
        this.cells.replaceAll(coordinate -> new Coordinate(coordinate.y, MAX_SIZE - 1 - coordinate.x));
        this.draw();
    }

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

