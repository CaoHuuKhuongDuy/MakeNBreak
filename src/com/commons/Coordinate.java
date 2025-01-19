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

    public Coordinate(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
        this.priority = 0;
    }

    public Coordinate(int x, int y, int priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
    }

    public double distance(Coordinate other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    // operator to plus two coordinates
    public Coordinate plus(Coordinate other) {
        return new Coordinate(this.x + other.x, this.y + other.y);
    }

    // operator to minus two coordinates
    public Coordinate minus(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }

    @Override
    public int compareTo(Coordinate other) {
        return Integer.compare(this.priority, other.priority);
    }
}
