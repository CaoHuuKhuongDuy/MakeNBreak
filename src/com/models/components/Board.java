package com.models.components;

import com.commons.Coordinate;
import com.models.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Vector;


public class Board extends Entity {

    private Color[][] occupied;
    private Coordinate[][] cells;
    private final int DEFAULT_ROW = 10, DEFAULT_COL = 15;

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

    private boolean canPlace(int x, int y) {
        return x >= 0 && x < DEFAULT_ROW && y >= 0 && y < DEFAULT_COL && this.occupied[x][y] == Color.TRANSPARENT;
    }

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

    public void setOccupied(int x, int y, Color color) {
        if (x < 0 || x >= DEFAULT_ROW || y < 0 || y >= DEFAULT_COL) return;
        this.occupied[x][y] = color;
    }

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

    @Override
    public void draw() {
        this.getChildren().clear();
        ImageView playBoard = new ImageView(new Image("/resources/assets/images/board.png"));
        this.getChildren().add(playBoard);
    }
}
