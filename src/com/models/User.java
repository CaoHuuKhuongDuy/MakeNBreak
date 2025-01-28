package com.models;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class User {
    private String name;
    private int point, userID, currentRound, solvedCard;
    private Text userPointText, userIDText, currentRoundText;



    public User(String name, int point) {
        this.name = name;
        this.point = point;
        Font jerseyFont = Font.loadFont(getClass().getResourceAsStream("/resources/assets/fonts/Jersey25.ttf"), 60);
        Font jerseyFontSmall = Font.loadFont(getClass().getResourceAsStream("/resources/assets/fonts/Jersey25.ttf"), 40);
        this.userPointText = new Text();
        this.userIDText = new Text();
        this.userPointText.setFont(jerseyFont);
        this.userIDText.setFont(jerseyFont);
        this.currentRoundText = new Text();
        this.currentRoundText.setFont(jerseyFontSmall);
        this.userID = 0;
        this.currentRound = 0;
        this.solvedCard = 0;
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

    public Text getCurrentRoundText() {
        return this.currentRoundText;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setCurrentRound(int round) {
        this.currentRound = round;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public void setSolvedCard(int solvedCard) {
        this.solvedCard = solvedCard;
    }

    public void increaseSolvedCard() {
        this.solvedCard++;
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

        String currentRoundT = "Round: " + (this.getCurrentRound() + 1);
        this.currentRoundText.setText(currentRoundT);
        this.currentRoundText.setFill(Color.WHITE);
        this.currentRoundText.setLayoutX(430);
        this.currentRoundText.setLayoutY(55);
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
