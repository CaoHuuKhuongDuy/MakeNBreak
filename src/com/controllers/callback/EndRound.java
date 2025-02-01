/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Represents a callback for ending a round of the game. Triggers the end-round logic
 * when the clock finishes counting down.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.controllers.callback;

import com.screens.GameScreen; // Represents the game screen of the application.

public class EndRound implements Runnable {
    private GameScreen gameScreen; // The game screen associated with this callback.

    public EndRound(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void run() {
        // End the round if the game is still playing.
        if (!this.gameScreen.getPlaying()) return;
        this.gameScreen.getGamePlay().endRound();
    }
}