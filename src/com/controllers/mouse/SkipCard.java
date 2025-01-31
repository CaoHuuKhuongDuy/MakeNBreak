/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: This class handles the mouse event for skipping a card. When the skip action is triggered,
 *         the card is either moved to the skipped cards collection or removed, depending on its status.
 *         Additionally, the user’s score is updated, and the appropriate animations are applied to the card.
 *
 * @Hong Minh Dao
 *
 * @Phan Khanh Linh Dang
 *
 * @version 1.0
 */

package com.controllers.mouse;

// Importing necessary classes for event handling and animations
import com.commons.Globals; // Provides global settings such as the game state and user data.
import com.models.Card; // Represents the card object that is being manipulated in the game.
import com.models.CardSet; // Manages the collection of cards in the game (opening, skipped, etc.).
import com.screens.GameScreen; // Represents the game screen where the card actions are visually displayed.
import javafx.animation.FadeTransition; // Used to animate the fading of a card when it's removed.
import javafx.animation.TranslateTransition; // Used to animate the translation (movement) of the card on the screen.
import javafx.event.EventHandler; // Interface for handling mouse events.
import javafx.scene.input.MouseEvent; // Represents the mouse event (click) on a card.
import javafx.util.Duration; // Specifies the duration of the animation.

import java.util.Vector; // A dynamic array that stores the cards being manipulated.

public class SkipCard implements EventHandler<MouseEvent>, Runnable {
    private CardSet cardSet; // Holds the card set containing opening, closing, skipped, and removed cards.
    private GenerateCard generateCard; // Generates new cards after actions are taken.
    private GameScreen gameScreen; // Reference to the game screen for user interaction and UI updates.

    /**
     * Constructor for initializing the SkipCard with the card set, card generator, and game screen.
     *
     * @param cardSet The set of cards used in the game.
     * @param generateCard The object that generates new cards after one is skipped.
     * @param gameScreen The game screen where actions are displayed and handled.
     */
    public SkipCard(CardSet cardSet, GenerateCard generateCard, GameScreen gameScreen) {
        this.cardSet = cardSet;
        this.generateCard = generateCard;
        this.gameScreen = gameScreen;
    }

    /**
     * Handles mouse click events on the cards. When a card is clicked, it triggers the skip action.
     * The action can either move the card to the skipped cards or remove it, depending on its state.
     *
     * @param event The mouse event triggered by the user clicking on the card.
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run(); // Trigger the skip action when the card is clicked.
        }
    }

    /**
     * Executes the skip action: removes the last card from the opening cards, adds it to the skipped cards
     * or removed cards, and applies animations for both actions. Updates the user’s points and regenerates a new card.
     */
    @Override
    public void run() {
        Vector<Card> openingCard = cardSet.getOpeningCards();
        if (openingCard.isEmpty()) return; // If there are no opening cards, do nothing.

        Vector<Card> skippedCards = cardSet.getSkippedCards();
        Card skippedCard = openingCard.getLast(); // Get the last card from the opening cards.
        openingCard.removeLast(); // Remove the card from the opening cards.

        TranslateTransition transition = new TranslateTransition(); // Create a translation animation for the card.
        if (!skippedCard.getSkipped()) {
            // If the card is not skipped yet, move it to the skipped cards and animate it.
            skippedCards.add(skippedCard);
            transition.setDuration(Duration.seconds(1.5)); // Set animation duration.
            transition.setNode(skippedCard); // Set the card as the node to animate.
            transition.setByX(280); // Move the card 280 units horizontally.
            transition.setCycleCount(1); // Only run the animation once.
            transition.setAutoReverse(false); // Do not reverse the animation after it completes.
            transition.setOnFinished(_ -> {
                skippedCard.setLayoutX(skippedCard.getLayoutX() + skippedCard.getTranslateX());
                skippedCard.setTranslateX(0); // Reset translation after the animation completes.
            });
        } else {
            // If the card is already skipped, move it off-screen and fade it out.
            Vector<Card> removedCards = cardSet.getRemovedCards();
            removedCards.add(skippedCard);
            transition.setDuration(Duration.seconds(1)); // Set a shorter animation for removal.
            transition.setNode(skippedCard); // Set the card as the node to animate.
            transition.setByY(-180); // Move the card off-screen vertically.
            transition.setCycleCount(1); // Only run the animation once.
            transition.setAutoReverse(false); // Do not reverse the animation.
            transition.setOnFinished(_ -> {
                // After the translation completes, apply a fade-out effect.
                FadeTransition fadeTransition = new FadeTransition();
                fadeTransition.setDuration(Duration.seconds(0.5)); // Fade-out duration.
                fadeTransition.setNode(skippedCard); // Set the card to fade out.
                fadeTransition.setFromValue(1.0); // Start fully visible.
                fadeTransition.setToValue(0.0); // Fade to invisible.
                fadeTransition.play(); // Start the fade-out animation.
            });
        }

        // Update the user’s points after skipping the card.
        int userID = (gameScreen.getUserID() + 1) % Globals.app.getUsers().size(); // Get the next user.
        Globals.getUser(userID).setPoint(Globals.getUser(userID).getPoint() + 1); // Increase the user's points.
        skippedCard.setSkipped(true); // Mark the card as skipped.
        transition.play(); // Start the movement animation.
        skippedCard.toFront(); // Bring the card to the front of the display.
        generateCard.run(true); // Generate a new card after skipping one.
    }
}
