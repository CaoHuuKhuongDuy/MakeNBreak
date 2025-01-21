package com.models;

import java.util.Vector;

public class CardSet {
    private final Vector<Card> openingCards, closingCards, skippedCards;

    public CardSet() {
        this.openingCards = new Vector<>();
        this.closingCards = new Vector<>();
        this.skippedCards = new Vector<>();
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
}
