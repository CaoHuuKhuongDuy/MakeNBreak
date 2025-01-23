package com.controllers.mouse;

import com.commons.Coordinate;
import com.commons.Globals;
import com.models.Card;
import com.models.CardSet;
import com.screens.GameScreen;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Vector;

public class SkipCard implements EventHandler<MouseEvent>, Runnable {
    private CardSet cardSet;
    private GenerateCard generateCard;
    private GameScreen gameScreen;

    public SkipCard(CardSet cardSet, GenerateCard generateCard, GameScreen gameScreen) {
        this.cardSet = cardSet;
        this.generateCard = generateCard;
        this.gameScreen = gameScreen;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run();
        }
    }

    @Override
    public void run() {
        Vector<Card> openingCard = cardSet.getOpeningCards();
        if (openingCard.isEmpty()) return;
        Vector <Card> skippedCards = cardSet.getSkippedCards();
        Card skippedCard = openingCard.getLast();
        openingCard.removeLast();
        TranslateTransition transition = new TranslateTransition();
        if (!skippedCard.getSkipped()) {
            skippedCards.add(skippedCard);
            transition.setDuration(Duration.seconds(1.5)); // Duration of the animation
            transition.setNode(skippedCard);              // Set the node to animate
            transition.setByX(280);                    // Move 280 units horizontally
            transition.setCycleCount(1);               // Only run once
            transition.setAutoReverse(false);          // Do not reverse
            transition.setOnFinished(_ -> {
                skippedCard.setLayoutX(skippedCard.getLayoutX() + skippedCard.getTranslateX());
                skippedCard.setTranslateX(0);
            });
        } else {
            Vector <Card> removedCards = cardSet.getRemovedCards();
            removedCards.add(skippedCard);
            transition.setDuration(Duration.seconds(1)); // Duration of the animation
            transition.setNode(skippedCard);              // Set the node to animate
            transition.setByY(-180);                    // Move 280 units horizontally
            transition.setCycleCount(1);               // Only run once
            transition.setAutoReverse(false);          // Do not reverse
            transition.setOnFinished(_ -> {
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.seconds(0.5)); // Duration of the fade-out
                fadeTransition.setNode(skippedCard);              // Set the node to animate
                fadeTransition.setFromValue(1.0);                 // Start fully visible
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
            });
        }
        int userID = (gameScreen.getUserID() + 1) % Globals.app.getUsers().size();
        Globals.getUser(userID).setPoint(Globals.getUser(userID).getPoint() + 1);
        skippedCard.setSkipped(true);
        transition.play();
        skippedCard.toFront();
        generateCard.run(true);
    }
}
