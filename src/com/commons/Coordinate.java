package com.commons;

public class Coordinate implements Comparable<Coordinate> {
    public int x, y, priority;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
        this.priority = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.priority = 0;
    }

    public Coordinate(int x, int y, int priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
    }

    // Override compareTo method to define custom comparison logic
    @Override
    public int compareTo(Coordinate other) {
        // Compare by priority (ascending order)
        return Integer.compare(this.priority, other.priority);
    }
}
