package com.entities;

import com.commons.Coordinate;

public class Card extends Entity {
    private int[][] cells;
    private int width, height;

    public Card(int width, int height, Coordinate position, boolean interactable) {
        super(position, interactable);
        this.width = width;
        this.height = height;
        this.cells = new int[width][height];
    }



}
