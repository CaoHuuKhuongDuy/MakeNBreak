package com.entities;
import com.commons.Coordinate;
import javafx.util.Pair;

public class Entity {
    protected Coordinate position;
    protected boolean interactable;

    public Entity() {
        this.position = new Coordinate(0, 0);
        this.interactable = false;
    }

    public Entity(Coordinate position, boolean interactable) {
        this.position = position;
        this.interactable = interactable;
    }

    public Coordinate getPosition() {
        return this.position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public boolean isInteractable() {
        return this.interactable;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
}
