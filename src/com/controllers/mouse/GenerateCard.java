package com.controllers.mouse;

import com.commons.Coordinate;
import com.models.BlockContainer;
import com.models.Card;
import com.models.CardSet;
import com.models.components.Board;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Vector;

public class GenerateCard implements EventHandler<MouseEvent>, Runnable  {
    private Vector <Card> openingCard, closingCard;
    private BlockContainer blockContainer;
    private Runnable callBack;

    public GenerateCard(CardSet cardSet, BlockContainer blockContainer) {
        this.blockContainer = blockContainer;
        this.openingCard = cardSet.getOpeningCards();
        this.closingCard = cardSet.getClosingCards();
    }

    public GenerateCard(Vector <Card> openingCard, Vector <Card> closingCard, BlockContainer blockContainer) {
        this.openingCard = openingCard;
        this.closingCard = closingCard;
        this.blockContainer = blockContainer;
    }

    public GenerateCard setCards(Vector <Card> openingCard, Vector <Card> closingCard) {
        this.openingCard = openingCard;
        this.closingCard = closingCard;
        return this;
    }

    public GenerateCard setCallBack(Runnable callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run();
        }
    }

    @Override
    public void run() {
        if (closingCard.isEmpty()) {
            callBack.run();
            return;
        }
        closingCard.getLast().setPosition(new Coordinate(420, 155));
        closingCard.getLast().setOpen(true);
        openingCard.add(closingCard.getLast());
        openingCard.getLast().toFront();
        blockContainer.setBlocks(openingCard.getLast().getBuildingBlocks());
        blockContainer.getBoard().reset();
        closingCard.removeLast();
    }
}
