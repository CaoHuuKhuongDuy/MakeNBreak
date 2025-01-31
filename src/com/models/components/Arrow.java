/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents an arrow in the game. Manages the arrow's geometry and provides
 * functionality to update its angle and position.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models.components;

import com.commons.Coordinate; // Represents a coordinate in the game grid.
import com.models.Entity; // Base class for game entities.
import javafx.scene.paint.Color; // Represents a color in the JavaFX color space.
import javafx.scene.shape.Line; // Represents a line in JavaFX.

public class Arrow extends Entity {
    private double length; // The length of the arrow.
    private double angle; // The angle of the arrow.

    private Line shaft; // The shaft of the arrow.
    private Line leftHead; // The left head of the arrow.
    private Line rightHead; // The right head of the arrow.

    private static final double HEAD_LENGTH = 10; // The length of the arrowhead.
    private static final double HEAD_ANGLE = Math.toRadians(30); // The angle of the arrowhead.
    private static final double THICKNESS = 3; // The thickness of the arrow lines.

    /**
     * Constructs an Arrow with the specified position, length, and angle.
     *
     * @param position The position of the arrow.
     * @param length   The length of the arrow.
     * @param angle    The angle of the arrow.
     */
    public Arrow(Coordinate position, double length, double angle) {
        super(position, true);
        this.length = length;
        this.angle = -angle;

        initializeArrow();
    }

    /**
     * Initializes the arrow's lines and styles them.
     */
    private void initializeArrow() {
        // Initialize the three lines of the arrow
        this.shaft = new Line();
        this.leftHead = new Line();
        this.rightHead = new Line();

        // Update the arrow geometry
        draw();

        // Style the lines
        styleLine(shaft);
        styleLine(leftHead);
        styleLine(rightHead);

        this.getChildren().addAll(shaft, leftHead, rightHead);
    }

    /**
     * Styles a line with a black color and a specified thickness.
     *
     * @param line The line to style.
     */
    private void styleLine(Line line) {
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(THICKNESS);
    }

    /**
     * Updates the geometry of the arrow based on the current position, length, and angle.
     */
    @Override
    public void draw() {
        double angleRadians = Math.toRadians(this.angle);

        // Calculate the end of the shaft
        double endX = this.position.x + length * Math.cos(angleRadians);
        double endY = this.position.y + length * Math.sin(angleRadians);

        // Update the shaft line
        shaft.setStartX(this.position.x);
        shaft.setStartY(this.position.y);
        shaft.setEndX(endX);
        shaft.setEndY(endY);

        // Calculate left head line
        double leftX = endX + HEAD_LENGTH * Math.cos(angleRadians + Math.PI - HEAD_ANGLE);
        double leftY = endY + HEAD_LENGTH * Math.sin(angleRadians + Math.PI - HEAD_ANGLE);
        leftHead.setStartX(endX);
        leftHead.setStartY(endY);
        leftHead.setEndX(leftX);
        leftHead.setEndY(leftY);

        // Calculate right head line
        double rightX = endX + HEAD_LENGTH * Math.cos(angleRadians + Math.PI + HEAD_ANGLE);
        double rightY = endY + HEAD_LENGTH * Math.sin(angleRadians + Math.PI + HEAD_ANGLE);
        rightHead.setStartX(endX);
        rightHead.setStartY(endY);
        rightHead.setEndX(rightX);
        rightHead.setEndY(rightY);
    }

    public double getAngle() {
        return -this.angle;
    }

    /**
     * Sets the angle of the arrow and updates its geometry.
     *
     * @param angle The new angle of the arrow.
     */
    public void setAngle(double angle) {
        this.angle = -angle;
        draw();
    }

    /**
     * Sets the position of the arrow and updates its geometry.
     *
     * @param position The new position of the arrow.
     */
    public void setPosition(Coordinate position) {
        this.position = position;
        draw();
    }
}