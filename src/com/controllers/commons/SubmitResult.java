/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Handles the submission of results for a card. Checks if the card matches the
 * current state of the block container and updates the user's points accordingly.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.controllers.commons;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.controllers.mouse.GenerateCard; // Handles the generation of cards.
import com.models.BlockContainer; // Represents the container for building blocks.
import com.models.Card; // Represents a card in the game.
import com.models.CardSet; // Represents a set of cards.
import com.models.User; // Represents a user in the application.
import javafx.event.Event; // Represents an event in JavaFX.
import javafx.event.EventHandler; // Interface for handling events in JavaFX.
import javafx.scene.input.KeyCode; // Represents a key code for keyboard events.
import javafx.scene.input.KeyEvent; // Represents a keyboard event in JavaFX.
import javafx.scene.input.MouseEvent; // Represents a mouse event in JavaFX.

import java.util.Vector; // A dynamic array implementation.

public class SubmitResult implements EventHandler<Event>, Runnable {
    private Vector<Card> openingCard; // The list of opening cards.
    private BlockContainer blockContainer; // The container for building blocks.
    private User user; // The current user.
    private GenerateCard generateCard; // Handles the generation of cards.

    /**
     * Constructs a SubmitResult object with the specified user ID, card set, generate card handler,
     * and block container.
     *
     * @param userID          The ID of the current user.
     * @param cardSet         The set of cards in the game.
     * @param generateCard    The handler for generating cards.
     * @param blockContainer  The container for building blocks.
     */
    public SubmitResult(int userID, CardSet cardSet, GenerateCard generateCard, BlockContainer blockContainer) {
        this.user = Globals.app.getUsers().get(userID);
        this.generateCard = generateCard;
        this.blockContainer = blockContainer;
        this.openingCard = cardSet.getOpeningCards();
    }

    /**
     * Sets the current user based on the specified user ID.
     *
     * @param userID The ID of the user.
     */
    public void setUser(int userID) {
        this.user = Globals.app.getUsers().get(userID);
    }

    @Override
    public void handle(Event event) {
        // Handle mouse click or spacebar press to submit the result.
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run();
            return;
        }
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode() == KeyCode.SPACE) {
                this.run();
            }
        }
    }

    @Override
    public void run() {
        // Check if the card matches the block container and update the user's points.
        if (openingCard.isEmpty()) return;
        Card card = openingCard.getLast();
        if (card != null && card.matching(blockContainer.getBoard().getOccupied(), 10, 15)) {
            user.setPoint(user.getPoint() + card.getPoint());
            user.updateUserInforText();
            this.generateCard.run(true); // Generate a new card.
        }
    }
}