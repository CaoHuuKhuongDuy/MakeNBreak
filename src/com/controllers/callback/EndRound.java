package com.controllers.callback;

import com.screens.GameScreen;

public class EndRound implements Runnable {
    private GameScreen gameScreen;

    public EndRound(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void run() {
        if (!this.gameScreen.getPlaying()) return;
        this.gameScreen.getGamePlay().endRound();
    }
}
