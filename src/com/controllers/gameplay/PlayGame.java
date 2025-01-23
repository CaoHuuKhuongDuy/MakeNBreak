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
            int remainingRounds = Globals.app.getNumberOfRound();

            if (remainingRounds > 1) {
                Globals.app.setNumberOfRound(remainingRounds - 1);
                this.gameScreen.setPlaying(true);
                this.gameScreen.getDice().setInteractable(true);
                this.gameScreen.initCards();
            } else {
                endGame();
                return;
            }
            this.gameScreen.updateUser(0);
        } else {
            this.gameScreen.updateUser(userID + 1);
        }

        if (Globals.app.getNumberOfRound() > 0) {
            playRound();
        }
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
