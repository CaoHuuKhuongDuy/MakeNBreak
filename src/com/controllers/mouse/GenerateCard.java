/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: This class is responsible for handling the generation of new cards in the game. It listens to mouse
 *         events and triggers the generation of new cards, updating the game state accordingly. It also handles
 *         the transfer of cards between different collections (opening/closing), and manages the block container
 *         by setting the blocks for the generated card.
 *
 * @Hong Minh Dao
 *
 * @Phan Khanh Linh Dang
 *
 * @version 1.0
 */

package com.controllers.mouse;

// Importing necessary classes for event handling and game state management
import com.commons.Coordinate; // Represents the coordinates used for positioning elements in the game.
import com.models.BlockContainer; // Handles the container of blocks associated with the current card.
import com.models.Card; // Represents the card object that is being generated and displayed in the game.
import com.models.CardSet; // Manages the collection of cards in the game (opening, closing, etc.).
import javafx.event.EventHandler; // Interface for handling mouse events.
import javafx.scene.input.MouseEvent; // Represents the mouse event (click) on the game interface.

import java.util.Vector; // A dynamic array to store and manipulate the collection of cards.

public class GenerateCard implements EventHandler<MouseEvent> {
    private CardSet cardSet; // The card set containing opening, closing, skipped, and removed cards.
    private BlockContainer blockContainer; // The container holding the blocks for the current card.
    private Runnable callBack; // A callback function that can be triggered after the card generation process.

    /**
     * Constructor for initializing the GenerateCard with the card set and block container.
     *
     * @param cardSet The set of cards used in the game.
     * @param blockContainer The container for blocks related to the generated card.
     */
    public GenerateCard(CardSet cardSet, BlockContainer blockContainer) {
        this.blockContainer = blockContainer;
        this.cardSet = cardSet;
    }

    /**
     * Sets a callback function to be executed after the card generation process.
     *
     * @param callBack The callback function to be executed.
     * @return The current GenerateCard instance, allowing for method chaining.
     */
    public GenerateCard setCallBack(Runnable callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * Handles mouse click events on the game interface. When a card is clicked, it triggers the card generation
     * process, updating the opening and closing card collections and the block container.
     *
     * @param event The mouse event triggered by the user clicking on the game screen.
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run(true); // Trigger the card generation process when a click event is detected.
        }
    }

    /**
     * Executes the card generation process: moves the last closing card to the opening cards collection,
     * updates its position, sets the blocks in the block container, and resets the board. If the closing cards are empty,
     * a new closing card is generated.
     *
     * @param generateNewCard A flag indicating whether to generate a new card or not.
     */
    public void run(boolean generateNewCard) {
        if (!generateNewCard) {
            callBack.run(); // Execute the callback function if no new card is being generated.
            return;
        }

        // Get the closing and opening cards from the card set
        Vector<Card> closingCard = cardSet.getClosingCards();
        Vector<Card> openingCard = cardSet.getOpeningCards();

        // Move the last closing card to the opening cards and update its position
        closingCard.getLast().setPosition(new Coordinate(420, 155)); // Set the position of the card.
        closingCard.getLast().setOpen(true); // Mark the card as open.
        openingCard.add(closingCard.getLast()); // Add the card to the opening cards collection.
        openingCard.getLast().toFront(); // Bring the card to the front of the display.

        // Set the building blocks in the block container for the newly opened card
        blockContainer.setBlocks(openingCard.getLast().getBuildingBlocks());
        blockContainer.getBoard().reset(); // Reset the board to reflect the new state.

        closingCard.removeLast(); // Remove the last closing card from the collection.

        // If there are no more closing cards, generate a new closing card
        if (closingCard.isEmpty()) {
            cardSet.genNewClosingCard();
        }
    }
}
