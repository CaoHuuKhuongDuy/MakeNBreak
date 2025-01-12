package com.models;

public class User {
    private String name;
    private int point;



    public User(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getPoint() {
        return this.point;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
