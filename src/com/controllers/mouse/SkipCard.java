package com.controllers.mouse;

import com.commons.Coordinate;
import com.models.Card;
import com.models.CardSet;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Vector;

public class SkipCard implements EventHandler<MouseEvent>, Runnable {
    private CardSet cardSet;
    private GenerateCard generateCard;

    public SkipCard(CardSet cardSet, GenerateCard generateCard) {
        this.cardSet = cardSet;
        this.generateCard = generateCard;
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
        skippedCards.add(skippedCard);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1.5)); // Duration of the animation
        transition.setNode(skippedCard);              // Set the node to animate
        transition.setByX(280);                    // Move 280 units horizontally
        transition.setCycleCount(1);               // Only run once
        transition.setAutoReverse(false);          // Do not reverse
        transition.setOnFinished(_ -> {
            skippedCard.setOpen(false);
            skippedCard.setLayoutX(skippedCard.getLayoutX() + skippedCard.getTranslateX());
            skippedCard.setTranslateX(0);
        });
//         Start the animation
        transition.play();
        generateCard.run();
    }
}
