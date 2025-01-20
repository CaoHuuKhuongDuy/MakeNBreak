package com.models;

import com.commons.Globals;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class User {
    private String name;
    private int point, userID;
    private Text userPointText, userIDText;



    public User(String name, int point) {
        this.name = name;
        this.point = point;
        Font jerseyFont = Font.loadFont(getClass().getResourceAsStream("/resources/assets/fonts/Jersey25.ttf"), 60);
        this.userPointText = new Text();
        this.userIDText = new Text();
        this.userPointText.setFont(jerseyFont);
        this.userIDText.setFont(jerseyFont);
        this.userID = 0;
    }

    public User(String name) {
        this(name, 0);
    }

    public String getName() {
        return this.name;
    }


    public Text getUserPointText() {
        return this.userPointText;
    }

    public Text getUserIDText() {
        return this.userIDText;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public int getUserID() {
        return this.userID;
    }

    public void updateUserInforText() {
        String userPointT = String.valueOf(this.getPoint());
        this.userPointText.setText(userPointT);
        this.userPointText.setFill(Color.YELLOW);
        this.userPointText.setLayoutX(910 - userPointText.getBoundsInLocal().getWidth());
        this.userPointText.setLayoutY(120);

        this.userIDText.setText(this.getName());
        this.userIDText.setFill(Color.RED);
        this.userIDText.setLayoutX(490);
        this.userIDText.setLayoutY(120);
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
