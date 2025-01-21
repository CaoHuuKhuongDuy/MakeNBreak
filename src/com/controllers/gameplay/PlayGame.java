package com.controllers.gameplay;

import com.commons.Globals;
import com.screens.GameScreen;

public class PlayGame {
    GameScreen gameScreen;
    public PlayGame(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void playRound() {
        this.gameScreen.setPlaying(true);
        this.gameScreen.getDice().setInteractable(true);
        this.gameScreen.initCards();
    }

    public void endRound() {
        this.gameScreen.setPlaying(false);
        this.gameScreen.getClock().reset();
        this.gameScreen.getDice().reset();
        this.gameScreen.getBlockContainer().reset();
        int userID = this.gameScreen.getUserID();
        if (userID == Globals.app.getUsers().size() - 1) {
            // TODO: Add logic to end game
            userID = 0;
        } else {
            this.gameScreen.updateUser(userID + 1);
        }
        this.playRound();
    }
}
