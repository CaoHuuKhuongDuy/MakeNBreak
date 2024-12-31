package com.entities.buildingblocks;

import com.commons.Coordinate;
import com.entities.Entity;
import com.commons.Coordinate;
import java.util.HashSet;
import java.util.Vector;
import javafx.scene.paint.Color;

import javax.swing.text.Position;


public class BuildingBlock extends Entity {
    private Vector<Coordinate > cells;
    private Color color;
    private boolean[][] occupied;
    private static final int MAX_SIZE = 20;

    public BuildingBlock(Vector<Coordinate> cells, Coordinate position, Color color, boolean interactable) {
        super(position, interactable);
        this.cells = cells;
        this.color = color;
        this.occupied = new boolean[MAX_SIZE][MAX_SIZE];
        for (Coordinate cell : cells) {
            this.occupied[cell.x][cell.y] = true;
        }
    }

    public BuildingBlock(Vector<Coordinate> cells) {
        super(new Coordinate(0, 0), true);
        this.color = Color.TRANSPARENT;
        this.cells = cells;
        this.occupied = new boolean[MAX_SIZE][MAX_SIZE];
        for (Coordinate cell : cells) {
            this.occupied[cell.x][cell.y] = true;
        }
    }

    public Vector <Coordinate> getCells() {
        return this.cells;
    }

    public Color getColor() {
        return this.color;
    }

    private boolean isCellOccupied(Coordinate cell) {
        if (cell.x < 0 || cell.x >= MAX_SIZE || cell.y < 0 || cell.y >= MAX_SIZE) {
            return false;
        }
        return this.occupied[cell.x][cell.y];
    }
}

