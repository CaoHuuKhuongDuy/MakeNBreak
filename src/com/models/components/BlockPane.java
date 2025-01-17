package com.models.components;

import com.commons.Coordinate;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlockPane extends Pane {
    private BuildingBlock block;

    private final int blockWidth = 60;
    private final int blockHeight = 60;
    private final int cellSpacing = 0;

    public BlockPane(BuildingBlock block) {
        this.block = block;

        // Render the block cells
        for (Coordinate cell : block.getCells()) {
            Rectangle cellRect = new Rectangle(blockWidth / 2.0, blockHeight / 2.0);
            cellRect.setFill(block.getColor());
            cellRect.setStroke(Color.BLACK);
            cellRect.setStrokeWidth(1);

            // Position the cell with the defined cell spacing
            cellRect.setX(cell.y * (blockWidth / 2.0 + cellSpacing));
            cellRect.setY(cell.x * (blockHeight / 2.0 + cellSpacing));
            this.getChildren().add(cellRect);
        }

    }

    public BuildingBlock getBlock() {
        return block;
    }
}
