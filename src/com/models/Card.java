package com.models;

import com.commons.Coordinate;
import com.commons.GameType;
import com.commons.Globals;
import com.commons.utils.Hashing;
import com.models.components.BuildingBlock;
import com.models.components.Grid;
import com.models.components.ListBuildingBlock;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.Random;
import java.util.Vector;

public class Card extends Entity {
    private Color[][] cells;
    private int point;
    private GameType type;
    private boolean open;
    private boolean skipped;
    private int row, col;
    private Color[][] bound;
    private int rowBound, colBound;
    private Vector <BuildingBlock> buildingBlocks;
    private int lowerBound;
    private int upperBound;

    public void setNumber(int number) {
        this.point = number;
    }

    public Card(ListBuildingBlock blockGenerator, int row, int col, Coordinate position, double width, double height, GameType type, boolean open,  int lowerBound, int upperBound) {
        super(position, true, width, height);
        Random random = new Random();
        this.type = type;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        if (this.type == GameType.MULTIPLE_BLOCK){
            this.point = random.nextInt(3) + 1;
        }
        else {
            this.point = 1;
        }
        this.open = open;
        this.buildingBlocks = new Vector<>();
        for (BuildingBlock block : blockGenerator.getBuildingBlocks()) {
            this.buildingBlocks.add(block.clone());
        }
        this.setCells(blockGenerator.generateBuilding(row, col, lowerBound, upperBound), row, col);
        this.skipped = false;
        this.draw();
    }

    public Vector<BuildingBlock> getBuildingBlocks() {
        return this.buildingBlocks;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
        this.draw();
    }

    private void setCells(Color[][] cells, int row, int col) {
        this.cells = cells;
        this.row = row;
        this.col = col;
        int minX, minY, maxX, maxY;
        minX = minY = Integer.MAX_VALUE;
        maxX = maxY = Integer.MIN_VALUE;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!cells[i][j].equals(Color.TRANSPARENT)) {
                    minX = Math.min(minX, i);
                    minY = Math.min(minY, j);
                    maxX = Math.max(maxX, i);
                    maxY = Math.max(maxY, j);
                }
            }
        }
        this.bound = new Color[maxX - minX + 1][maxY - minY + 1];
        this.rowBound = maxX - minX + 1;
        this.colBound = maxY - minY + 1;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                this.bound[i - minX][j - minY] = cells[i][j];
            }
        }
        this.draw();
    }

    public void setOpen(boolean open) {
        this.open = open;
        this.draw();
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public boolean getSkipped() {
        return this.skipped;
    }

    public void draw() {
        this.getChildren().clear();
        this.canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (!open) {
            ImageView imageViewCard = new ImageView(Globals.getResource("/resources/assets/images/back_of_card.png"));
            imageViewCard.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
            this.getChildren().add(imageViewCard);
            return;
        }
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        // Define colors for multi and single types
        Color multiBorder = Color.rgb(255, 124, 87);
        Color multiScore = Color.RED;
        Color singleBorder = Color.rgb(87, 163, 255);
        Color singleScore = Color.BLUE;

        // set color the background
        Rectangle playground = new Rectangle(0, 0, this.width, this.height);
        Color color = Color.rgb(255, 255, 191); // Fully opaque
        playground.setFill(color);
        double cornerRadius = 10;
        playground.setArcWidth(cornerRadius);
        playground.setArcHeight(cornerRadius);

        // Set border color on card type
        if (this.type == GameType.MULTIPLE_BLOCK) {
            playground.setStroke(multiBorder);
        } else {
            playground.setStroke(singleBorder);
        }

        playground.setStrokeWidth(this.width * 0.02);
        this.getChildren().add(playground);

        Rectangle line = new Rectangle(this.width * 0.008, this.height * 0.91, this.width * 0.983, this.height * 0.01);
        color = Color.rgb(255,230,87);
        line.setFill(color);
        this.getChildren().add(line);

        double tmp = Math.min(width, height);
        double gridSize = tmp * 1.1;
        Grid grid = new Grid(this.cells, new Coordinate((int) ((this.width - gridSize) * 0.49), (int) (this.height * 1.28 - gridSize)), gridSize, gridSize);
        grid.draw();
        this.getChildren().add(grid);

        gc.setFill(this.type == GameType.MULTIPLE_BLOCK ? multiScore : singleScore);  // Color based on type
        Font jerseyFont = Font.loadFont(Globals.getResource("/resources/assets/fonts/Jersey25.ttf"), tmp * 0.18);
        gc.setFont(jerseyFont);
        gc.fillText(Integer.toString(this.point), this.width * 0.03, this.height * 0.15); // Draw the number
        gc.fillText(Integer.toString(this.point), this.width * 0.92, this.height * 0.15); // Draw the number

        this.getChildren().add(canvas);
    }

    public boolean matching(Color[][] matrix, int row, int col) {
        Hashing hashing = new Hashing().setResultMatrix(this.bound, this.rowBound, this.colBound).setCompareMatrix(matrix, row, col);
        return hashing.compare();
    }
}
