package com.models.components;

import com.commons.Coordinate;
import com.models.Entity;

import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class BuildingBlock extends Entity {
    private Vector<Coordinate > cells;
    private Color color;
    private static final int MAX_SIZE = 3;
    private int cellSpacing = 0;

    public BuildingBlock(Vector<Coordinate> cells, Coordinate position, Color color, boolean interactable) {
        super(position, interactable);
        this.cells = cells;
        this.color = color;
    }

    public BuildingBlock(Vector<Coordinate> cells) {
        super(new Coordinate(0, 0), true);
        this.color = Color.TRANSPARENT;
        this.cells = cells;
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
        for (Coordinate cell : this.getCells()) {
            Rectangle cellRect = new Rectangle(this.width / 2.0, this.height / 2.0);
            cellRect.setFill(this.getColor());
            cellRect.setStroke(Color.BLACK);
            cellRect.setStrokeWidth(1);

            cellRect.setX(cell.y * (this.width / 2.0 + cellSpacing));
            cellRect.setY(cell.x * (this.height / 2.0 + cellSpacing));
            this.getChildren().add(cellRect);
        }
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
}

