package com.models.components;

import com.commons.Coordinate;
import com.models.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


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

    public Coordinate snapToGrid(Coordinate position) {
        position = position.minus(this.position);
        position = position.plus(new Coordinate(20, 20));

        Coordinate closetCell = new Coordinate(0, 0);
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < DEFAULT_ROW; i++) {
            for (int j = 0; j < DEFAULT_COL; j++) {
                double distance = position.distance(this.cells[i][j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closetCell = this.cells[i][j];
                }
            }
        }
        closetCell = closetCell.minus(new Coordinate(20, 20));
        return closetCell.plus(this.position);
    }

    @Override
    public void draw() {
        this.getChildren().clear();
        ImageView playBoard = new ImageView(new Image("/resources/assets/images/board.png"));
        this.getChildren().add(playBoard);

        // test coordinate of cells
        for (int i = 0; i < DEFAULT_ROW; i++) {
            for (int j = 0; j < DEFAULT_COL; j++) {
                Circle cell = new Circle(this.cells[i][j].x, this.cells[i][j].y, 1);
                cell.setFill(Color.RED);
                this.getChildren().add(cell);
            }
        }
    }
}
