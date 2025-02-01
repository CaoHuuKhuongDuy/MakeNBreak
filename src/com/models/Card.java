/**
 * OOP Java Project  WiSe 2024/2025
 * Purpose: Represents a card in the game, containing a building block configuration and point value.
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.models;

import com.commons.Coordinate; // Import for coordinate handling in the game world
import com.commons.GameType; // Import for handling different game types (e.g., MULTIPLE_BLOCK)
import com.commons.Globals; // Import for accessing global variables and resources
import com.commons.utils.Hashing; // Import for comparing the card's structure to a matrix using hashing
import com.models.components.BuildingBlock; // Import for handling building blocks that make up the card's structure
import com.models.components.Grid; // Import for rendering a grid representing the building's layout
import com.models.components.ListBuildingBlock; // Import for generating random building blocks
import javafx.scene.canvas.GraphicsContext; // Import for drawing on the canvas
import javafx.scene.image.ImageView; // Import for displaying images on the card
import javafx.scene.paint.Color; // Import for defining colors used in the card's design
import javafx.scene.shape.Rectangle; // Import for drawing rectangular shapes (card and border)
import javafx.scene.text.Font; // Import for font handling when displaying point values
import java.util.Random; // Import for random number generation (used for point assignment)
import java.util.Vector; // Import for using Vector to store building blocks and cards

public class Card extends Entity {
    private Color[][] cells;
    private int point;
    private GameType type;
    private boolean open;
    private boolean skipped;
    private int row, col;
    private Color[][] bound;
    private int rowBound, colBound;
    private Vector<BuildingBlock> buildingBlocks;
    private int lowerBound;
    private int upperBound;

    /**
     * Sets the point value for the card.
     * @param number The point value to be set.
     */
    public void setNumber(int number) {
        this.point = number;
    }

    /**
     * Constructs a new Card with the given parameters and generates its building blocks.
     * @param blockGenerator The block generator for generating the building blocks.
     * @param row The number of rows in the card's grid.
     * @param col The number of columns in the card's grid.
     * @param position The position of the card in the game world.
     * @param width The width of the card.
     * @param height The height of the card.
     * @param type The type of the game (MULTIPLE_BLOCK or single).
     * @param open Whether the card is open or not.
     * @param lowerBound The lower bound for the building block generation.
     * @param upperBound The upper bound for the building block generation.
     */
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


    /**
     * Sets the cells representing the card's structure and calculates its bounds.
     * @param cells The 2D array of colors representing the card's structure.
     * @param row The number of rows in the card's grid.
     * @param col The number of columns in the card's grid.
     */
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

    /**
     * Sets the open state of the card and redraws it.
     * @param open Whether the card is open or not.
     */
    public void setOpen(boolean open) {
        this.open = open;
        this.draw();
    }

    public boolean getOpen() {
        return this.open;
    }

    /**
     * Sets the skipped state of the card.
     * @param skipped Whether the card is skipped or not.
     */
    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    /**
     * Returns whether the card is skipped.
     * @return true if the card is skipped, false otherwise.
     */
    public boolean getSkipped() {
        return this.skipped;
    }

    /**
     * Draws the card on the screen with the appropriate visual elements.
     */
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

    /**
     * Checks if the given matrix matches the card's building layout.
     * @param matrix The matrix to compare against.
     * @param row The number of rows in the matrix.
     * @param col The number of columns in the matrix.
     * @return true if the matrix matches the card's layout, false otherwise.
     */
    public boolean matching(Color[][] matrix, int row, int col) {
        Hashing hashing = new Hashing().setResultMatrix(this.bound, this.rowBound, this.colBound).setCompareMatrix(matrix, row, col);
        return hashing.compare();
    }
}
