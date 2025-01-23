package com.controllers.gameplay;

import com.commons.Globals;
import com.models.User;
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
        Globals.getUser(userID).setCurrentRound(Globals.getUser(userID).getCurrentRound() + 1);
        userID = (userID + 1) % Globals.app.getUsers().size();
        if (Globals.getUser(userID).getCurrentRound() >= Globals.app.getNumberOfRound()) {
            this.endGame();
            return;
        }
        this.gameScreen.updateUser(userID);
        this.playRound();
    }

    private void endGame() {
        this.gameScreen.setPlaying(false);
        this.gameScreen.getClock().reset();
        this.gameScreen.getDice().reset();
        this.gameScreen.getBlockContainer().reset();

        this.gameScreen.handleEndGame();
        Globals.app.setNumberOfRound(0);
    }

}
