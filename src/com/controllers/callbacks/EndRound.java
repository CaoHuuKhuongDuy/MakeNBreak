package com.controllers.callbacks;


import com.screens.GameScreen;

public class EndRound implements Runnable {
    private GameScreen gameScreen;

    public EndRound(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void run() {
        this.gameScreen.EndRound();
    }
}
