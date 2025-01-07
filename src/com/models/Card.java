package com.models;

import com.commons.Coordinate;
import com.commons.GameType;
import com.models.components.Grid;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.Random;

public class Card extends Entity {
    private Color[][] cells;
    private int number;
    private GameType type;

    public void setNumber(int number) {
        this.number = number;
    }
    public void setType(GameType type) {
        this.type = type;
    }

    //Single block by default
    public Card(Color[][] cells, Coordinate position, double width, double height) {
        super(position, true, width, height);
        this.cells = cells;
        this.number = 1;
        this.type = GameType.SINGLE_BLOCK; // Default to SINGLE type
    }

    // Card with number and type (CardType enum)
    public Card(Color[][] cells, Coordinate position, double width, double height, GameType type) {
        super(position, true, width, height);
        this.cells = cells;
        Random random = new Random();
        this.number = random.nextInt(3) + 1;
        this.type = type;
    }

    public void draw() {
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
        gc.setFont(new Font("Arial", tmp * 0.15));
        gc.fillText(Integer.toString(this.number), this.width * 0.03, this.height * 0.15); // Draw the number
        gc.fillText(Integer.toString(this.number), this.width * 0.92, this.height * 0.15); // Draw the number

        this.getChildren().add(canvas);
    }
}
