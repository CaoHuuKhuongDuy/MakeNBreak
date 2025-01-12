package com.models.components;

import com.commons.Coordinate;
import com.models.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Arrow extends Entity {
    private double length; // Length of the arrow shaft
    private double angle;  // Angle of the arrow in degrees

    private Line shaft;    // The main shaft of the arrow
    private Line leftHead; // Left side of the arrowhead
    private Line rightHead; // Right side of the arrowhead

    private static final double HEAD_LENGTH = 10; // Length of the arrowhead lines
    private static final double HEAD_ANGLE = Math.toRadians(30); // Angle of the arrowhead (30 degrees)
    private static final double THICKNESS = 3; // Thickness of the arrow lines

    public Arrow(Coordinate position, double length, double angle) {
        super(position, true);
        this.length = length;
        this.angle = -angle;

        initializeArrow();
    }

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

        // Add the lines to this Entity
        this.getChildren().addAll(shaft, leftHead, rightHead);
    }

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

    public double getLength() {
        return this.length;
    }

    public void setLength(double length) {
        this.length = length;
        draw();
    }

    public double getAngle() {
        return -this.angle;
    }

    public void setAngle(double angle) {
        this.angle = -angle;
        draw();
    }

    public void setPosition(Coordinate position) {
        this.position = position;
        draw();
    }
}
