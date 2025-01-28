package com.screens;

import com.controllers.mouse.SwitchScreen;
import com.models.User;
import com.models.components.CustomButton;
import com.commons.Globals;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Comparator;

public class LeaderboardScreen extends Screen {

    private SwitchScreen switchScreen;

    public LeaderboardScreen(Stage primaryStage) {
        super(primaryStage);
        this.initHandlers();
        setupBackground("/resources/assets/images/leaderBoard.png");
    }

    @Override
    public void initHandlers() {
        this.switchScreen = new SwitchScreen(primaryStage);
    }

    @Override
    public void display() {
        this.getChildren().clear();
        this.getStylesheets().add(Globals.getResource("resources/assets/styles/Text.css"));

        Image firstPlace = new Image(Globals.getResource("/resources/assets/images/Trophy.png"));
        ImageView firstPlaceView = new ImageView(firstPlace);

        Image secondPlace = new Image(Globals.getResource("/resources/assets/images/Gold.png"));
        ImageView secondPlaceView = new ImageView(secondPlace);

        Image thirdPlace = new Image(Globals.getResource("/resources/assets/images/Silver.png"));
        ImageView thirdPlaceView = new ImageView(thirdPlace);

        Image fourthPlace = new Image(Globals.getResource("/resources/assets/images/Bronze.png"));
        ImageView fourthPlaceView = new ImageView(fourthPlace);

        Globals.app.getUsers().sort(Comparator.comparingInt(User::getPoint).reversed());

        int numberOfUsers = Globals.app.getUsers().size();

        if (numberOfUsers >= 4) {
            addLeaderboardEntry(firstPlaceView, Globals.app.getUsers().get(0), 474, 248);
            addLeaderboardEntry(secondPlaceView, Globals.app.getUsers().get(1), 142, 442);
            addLeaderboardEntry(thirdPlaceView, Globals.app.getUsers().get(2), 460, 442);
            addLeaderboardEntry(fourthPlaceView, Globals.app.getUsers().get(3), 767, 442);
        } else if (numberOfUsers == 3) {
            addLeaderboardEntry(secondPlaceView, Globals.app.getUsers().get(0), 461, 249);
            addLeaderboardEntry(thirdPlaceView, Globals.app.getUsers().get(1), 191, 429);
            addLeaderboardEntry(fourthPlaceView, Globals.app.getUsers().get(2), 728, 429);
        } else if (numberOfUsers == 2) {
            addLeaderboardEntry(secondPlaceView, Globals.app.getUsers().get(0), 265, 337);
            addLeaderboardEntry(thirdPlaceView, Globals.app.getUsers().get(1), 656, 328);
        } else if (numberOfUsers == 1) {
            addLeaderboardEntry(firstPlaceView, Globals.app.getUsers().getFirst(), 474, 330);
        }

        CustomButton homeButton = new CustomButton("", "resources/assets/images/Button/home.png");
        homeButton.setOnMouseClicked(this.switchScreen.setScreen(new IntroScreen(primaryStage)));

        homeButton.setLayoutX(485);
        homeButton.setLayoutY(664);
        this.getChildren().add(homeButton);

        this.primaryStage.getScene().setRoot(this);
    }


    private void addLeaderboardEntry(ImageView placeView, User user, double x, double y) {
        placeView.setLayoutX(x);
        placeView.setLayoutY(y);

        Label userIdLabel = new Label("Player: " + user.getUserID());
        userIdLabel.getStyleClass().add("label-user-id");

        Label scoreLabel = new Label("Score: " + user.getPoint());
        scoreLabel.getStyleClass().add("label-score");

        userIdLabel.setLayoutX(x);
        userIdLabel.setLayoutY(y + placeView.getImage().getHeight() + 10);

        scoreLabel.setLayoutX(x);
        scoreLabel.setLayoutY(userIdLabel.getLayoutY() + 30);

        this.getChildren().addAll(placeView, userIdLabel, scoreLabel);
    }
}
