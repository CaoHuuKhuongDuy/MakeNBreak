package com.controllers.commons;

import com.commons.Globals;
import com.controllers.mouse.GenerateCard;
import com.models.BlockContainer;
import com.models.Card;
import com.models.User;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Vector;

public class SubmitResult implements EventHandler<Event>, Runnable {
    private Vector <Card> openingCard;
    private BlockContainer blockContainer;
    private User user;
    private GenerateCard generateCard;

    public SubmitResult(int userID, Vector <Card> openingCard, GenerateCard generateCard, BlockContainer blockContainer) {
        this.user = Globals.app.getUsers().get(userID);
        this.generateCard = generateCard;
        this.blockContainer = blockContainer;
        this.openingCard = openingCard;
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            this.run();
            return;
        }
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode() == KeyCode.ENTER) {
                this.run();
            }
        }
    }

    @Override
    public void run() {
        if (openingCard.isEmpty()) return;
        Card card = openingCard.getLast();
        if (card != null && card.matching(blockContainer.getBoard().getOccupied(), 10, 15)) {
            user.setPoint(user.getPoint() + card.getPoint());
            user.updateUserInforText();
            this.generateCard.run();
        }
    }


}
