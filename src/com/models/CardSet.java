package com.models;

import java.util.Vector;

public class CardSet {
    private Vector<Card> openingCards, closingCards;

    public CardSet() {
        this.openingCards = new Vector<>();
        this.closingCards = new Vector<>();
    }

    public Vector <Card> getOpeningCards() {
        return this.openingCards;
    }

    public Vector <Card> getClosingCards() {
        return this.closingCards;
    }
}
