package com.controllers.mouse;

import com.commons.Coordinate;
import com.models.BlockContainer;
import com.models.Card;
import com.models.CardSet;
import com.screens.GameScreen;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Vector;

public class GenerateCard implements EventHandler<MouseEvent>  {
    private CardSet cardSet;
    private BlockContainer blockContainer;
    private Runnable callBack;

    public GenerateCard(CardSet cardSet, BlockContainer blockContainer) {
        this.blockContainer = blockContainer;
        this.cardSet = cardSet;
    }

    public GenerateCard setCallBack(Runnable callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run(true);
        }
    }

    public void run(boolean generateNewCard) {
        if (!generateNewCard) {
            callBack.run();
            return;
        }
        Vector <Card> closingCard = cardSet.getClosingCards();
        Vector <Card> openingCard = cardSet.getOpeningCards();
        closingCard.getLast().setPosition(new Coordinate(420, 155));
        closingCard.getLast().setOpen(true);
        openingCard.add(closingCard.getLast());
        openingCard.getLast().toFront();
        blockContainer.setBlocks(openingCard.getLast().getBuildingBlocks());
        blockContainer.getBoard().reset();
        closingCard.removeLast();
        if (closingCard.isEmpty()) {
            cardSet.genNewClosingCard();
        }
    }
}
