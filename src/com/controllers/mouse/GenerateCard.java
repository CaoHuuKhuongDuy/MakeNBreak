package com.controllers.mouse;

import com.commons.Coordinate;
import com.models.Card;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Vector;

public class GenerateCard implements EventHandler<MouseEvent>  {
    private Vector <Card> openingCard, closingCard;

    public GenerateCard(Vector <Card> openingCard, Vector <Card> closingCard) {
        this.openingCard = openingCard;
        this.closingCard = closingCard;
    }

    public GenerateCard setCards(Vector <Card> openingCard, Vector <Card> closingCard) {
        this.openingCard = openingCard;
        this.closingCard = closingCard;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (closingCard.isEmpty()) {
                return;
            }
            closingCard.getLast().setPosition(new Coordinate(420, 155));
            closingCard.getLast().setOpen(true);
            openingCard.add(closingCard.getLast());
            closingCard.removeLast();
        }
    }
}
