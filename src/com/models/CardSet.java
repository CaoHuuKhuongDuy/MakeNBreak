package com.models;

import com.commons.Coordinate;
import com.commons.Globals;
import com.models.components.BuildingBlock;
import com.models.components.ListBuildingBlock;
import com.screens.GameScreen;

import java.util.Vector;

public class CardSet {
    private final Vector<Card> openingCards, closingCards, skippedCards, removedCards;
    private ListBuildingBlock blockGenerator;
    private GameScreen gameScreen;
    private int numBlock;


    public CardSet(GameScreen gameScreen, int numBlock) {
        this.openingCards = new Vector<>();
        this.closingCards = new Vector<>();
        this.skippedCards = new Vector<>();
        this.removedCards = new Vector<>();
        this.blockGenerator = new ListBuildingBlock();
        this.gameScreen = gameScreen;
        this.numBlock = numBlock;
    }

    public Vector <Card> getOpeningCards() {
        return this.openingCards;
    }

    public Vector <Card> getClosingCards() {
        return this.closingCards;
    }

    public Vector <Card> getSkippedCards() {
        return this.skippedCards;
    }

    public void genNewClosingCard() {
        Vector <BuildingBlock> block = this.blockGenerator.generateRandomBuildingBlocks(numBlock, Globals.app.getGameType());
        this.blockGenerator.setBuildingBlocks(block);
        this.closingCards.add(new Card(this.blockGenerator, 10, 15, new Coordinate(700, 155), 261, 174, Globals.app.getGameType(), false, numBlock/2, numBlock));
        this.gameScreen.getChildren().add(this.closingCards.lastElement());
        this.closingCards.lastElement().toBack();
    }

    public Vector <Card> getRemovedCards() {
        return this.removedCards;
    }
}
