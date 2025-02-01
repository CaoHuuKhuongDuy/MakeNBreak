/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a user in the application. Manages user-related information such as name,
 * points, user ID, and current round. Also handles the display of user information in the UI.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.models;

import javafx.scene.paint.Color; // Represents a color in the JavaFX color space.
import javafx.scene.text.Font; // Represents a font for text rendering.
import javafx.scene.text.Text; // Represents a text node in JavaFX.

public class User {
    private String name; // The name of the user.
    private int point, userID, currentRound; // The user's points, ID, and current round.
    private Text userPointText, userIDText, currentRoundText; // Text elements for displaying user information.

    /**
     * Constructs a User object with a name and initial points.
     *
     * @param name  The name of the user.
     * @param point The initial points of the user.
     */
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

    /**
     * Updates the user information text displayed in the UI.
     */
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