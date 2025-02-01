/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Abstract base class for all game entities. Provides common functionality such as
 * position management, interactability, and drawing. Entities are represented as JavaFX Panes.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models;

import com.commons.Coordinate; // Represents a coordinate in the game grid.
import com.commons.Globals; // Provides global constants and utility methods for the application.
import javafx.scene.canvas.Canvas; // A node used for drawing graphics.
import javafx.scene.layout.Pane; // A base class for layout containers.

public abstract class Entity extends Pane {
    protected Coordinate position; // The position of the entity.
    protected boolean interactable; // Whether the entity is interactable.
    protected Canvas canvas; // A canvas for drawing the entity.
    protected double width, height; // The width and height of the entity.

    /**
     * Constructs an Entity with default values.
     */
    public Entity() {
        this.width = Globals.DEFAULT_WIDTH;
        this.height = Globals.DEFAULT_HEIGHT;
        this.canvas = new Canvas(width, height);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.position = new Coordinate(0, 0);
        this.interactable = false;
    }

    /**
     * Constructs an Entity with specified position and interactability.
     *
     * @param position     The position of the entity.
     * @param interactable Whether the entity is interactable.
     */
    public Entity(Coordinate position, boolean interactable) {
        this();
        this.position = position;
        this.interactable = interactable;
        this.setLayoutX(position.x);
        this.setLayoutY(position.y);
    }

    /**
     * Constructs an Entity with specified position, interactability, width, and height.
     *
     * @param position     The position of the entity.
     * @param interactable Whether the entity is interactable.
     * @param width        The width of the entity.
     * @param height       The height of the entity.
     */
    public Entity(Coordinate position, boolean interactable, double width, double height) {
        this.position = position;
        this.interactable = interactable;
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setLayoutX(position.x);
        this.setLayoutY(position.y);
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.draw();
    }

    public void setPosition(Coordinate position) {
        this.position = position;
        this.setLayoutX(position.x);
        this.setLayoutY(position.y);
        this.draw();
    }

    /**
     * Abstract method to draw the entity.
     */
    public abstract void draw();

    public boolean isInteractable() {
        return this.interactable;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
}