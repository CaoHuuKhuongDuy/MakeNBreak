/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a coordinate in a 2D space. Provides functionality for basic coordinate
 * operations such as addition, subtraction, distance calculation, and comparison based on priority.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.commons;

public class Coordinate implements Comparable<Coordinate> {
    public int x, y, priority; // The x, y coordinates and priority for comparison.

    /**
     * Constructs a Coordinate with default values (0, 0, 0).
     */
    public Coordinate() {
        this.x = 0;
        this.y = 0;
        this.priority = 0;
    }

    /**
     * Constructs a Coordinate with specified x and y values.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.priority = 0;
    }

    /**
     * Constructs a Coordinate with specified x and y values (converted from double to int).
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coordinate(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
        this.priority = 0;
    }

    /**
     * Constructs a Coordinate with specified x, y, and priority values.
     *
     * @param x        The x-coordinate.
     * @param y        The y-coordinate.
     * @param priority The priority for comparison.
     */
    public Coordinate(int x, int y, int priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
    }

    /**
     * Calculates the Euclidean distance between this coordinate and another.
     *
     * @param other The other coordinate.
     * @return The distance between the two coordinates.
     */
    public double distance(Coordinate other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     * Adds two coordinates together.
     *
     * @param other The other coordinate.
     * @return A new Coordinate representing the sum.
     */
    public Coordinate plus(Coordinate other) {
        return new Coordinate(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtracts another coordinate from this one.
     *
     * @param other The other coordinate.
     * @return A new Coordinate representing the difference.
     */
    public Coordinate minus(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }

    /**
     * Compares this coordinate to another based on priority.
     *
     * @param other The other coordinate.
     * @return A negative integer, zero, or a positive integer if this coordinate's priority
     *         is less than, equal to, or greater than the other's priority.
     */
    @Override
    public int compareTo(Coordinate other) {
        return Integer.compare(this.priority, other.priority);
    }
}