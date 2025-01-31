/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: This class manages a set of game cards, handling operations such as adding new cards,
 *         storing different types of cards (opening, closing, skipped, and removed),
 *         and interacting with the game screen.
 *
 * @Hong Minh Dao
 *
 * @Phan Khanh Linh Dang
 *
 * @version 1.0
 */

package com.models;

// Importing classes for game components and functionality
import com.commons.Coordinate; // Provides coordinate handling for positioning objects in the game.
import com.commons.Globals; // Holds global constants and settings for the game, such as game type.
import com.models.components.BuildingBlock; // Represents a single building block in the game.
import com.models.components.ListBuildingBlock; // Manages and generates a list of building blocks.
import com.screens.GameScreen; // Represents the screen where the game is played, allowing manipulation of the game elements.

import java.util.Vector; // A dynamic array that stores cards in different stages of the game (e.g., opening, closing).

public class CardSet {
    // Declaring member variables to hold different types of cards
    private final Vector<Card> openingCards, closingCards, skippedCards, removedCards;
    private ListBuildingBlock blockGenerator; // Generates and manages building blocks for cards.
    private GameScreen gameScreen; // Reference to the game screen to display cards.
    private int numBlock; // The number of blocks for a card.

    /**
     * Constructor for initializing the CardSet with the game screen and the number of blocks.
     *
     * @param gameScreen Reference to the GameScreen object for displaying cards.
     * @param numBlock Number of blocks to be used in generating the cards.
     */
    public CardSet(GameScreen gameScreen, int numBlock) {
        this.openingCards = new Vector<>();
        this.closingCards = new Vector<>();
        this.skippedCards = new Vector<>();
        this.removedCards = new Vector<>();
        this.blockGenerator = new ListBuildingBlock();
        this.gameScreen = gameScreen;
        this.numBlock = numBlock;
    }

    /**
     * Gets the vector of opening cards.
     *
     * @return A vector containing all the opening cards.
     */
    public Vector<Card> getOpeningCards() {
        return this.openingCards;
    }

    /**
     * Gets the vector of closing cards.
     *
     * @return A vector containing all the closing cards.
     */
    public Vector<Card> getClosingCards() {
        return this.closingCards;
    }

    /**
     * Gets the vector of skipped cards.
     *
     * @return A vector containing all the skipped cards.
     */
    public Vector<Card> getSkippedCards() {
        return this.skippedCards;
    }

    /**
     * Generates a new closing card with random building blocks and adds it to the game screen.
     */
    public void genNewClosingCard() {
        // Generate random building blocks based on the game type and number of blocks
        Vector<BuildingBlock> block = this.blockGenerator.generateRandomBuildingBlocks(numBlock, Globals.app.getGameType());
        this.blockGenerator.setBuildingBlocks(block);

        // Create a new closing card with the generated blocks and add it to the game screen
        this.closingCards.add(new Card(this.blockGenerator, 10, 15, new Coordinate(700, 155), 261, 174, Globals.app.getGameType(), false, numBlock/2, numBlock));
        this.gameScreen.getChildren().add(this.closingCards.lastElement());
        this.closingCards.lastElement().toBack();
    }

    /**
     * Gets the vector of removed cards.
     *
     * @return A vector containing all the removed cards.
     */
    public Vector<Card> getRemovedCards() {
        return this.removedCards;
    }
}
