/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Manages the gameplay logic for each round of the game. Handles starting and ending
 * rounds, updating the game state, and transitioning between rounds or ending the game.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.controllers.gameplay;

import com.commons.Globals; // Provides global constants and utility methods for the application.
import com.screens.GameScreen; // Represents the game screen of the application.

public class PlayGame {
    private GameScreen gameScreen; // The game screen associated with this gameplay logic.

    public PlayGame(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Starts a new round of the game. Enables the dice and initializes the cards.
     */
    public void playRound() {
        this.gameScreen.setPlaying(true);
        this.gameScreen.getDice().setInteractable(true);
        this.gameScreen.initCards();
    }

    /**
     * Ends the current round of the game. Resets the clock, dice, and block container,
     * and transitions to the next round or ends the game if all rounds are completed.
     */
    public void endRound() {
        this.gameScreen.setPlaying(false);
        this.gameScreen.getClock().reset();
        this.gameScreen.getDice().reset();
        this.gameScreen.getBlockContainer().reset();

        // Update the current round for the user.
        int userID = this.gameScreen.getUserID();
        Globals.getUser(userID).setCurrentRound(Globals.getUser(userID).getCurrentRound() + 1);

        // Transition to the next user or end the game if all rounds are completed.
        userID = (userID + 1) % Globals.app.getUsers().size();
        if (Globals.getUser(userID).getCurrentRound() >= Globals.app.getNumberOfRound()) {
            this.endGame();
            return;
        }

        // Update the game screen for the next user and start a new round.
        this.gameScreen.updateUser(userID);
        this.playRound();
    }

    /**
     * Ends the game. Resets the game state and triggers the end-game logic.
     */
    private void endGame() {
        this.gameScreen.setPlaying(false);
        this.gameScreen.getClock().reset();
        this.gameScreen.getDice().reset();
        this.gameScreen.getBlockContainer().reset();

        // Handle the end-game logic and reset the number of rounds.
        this.gameScreen.handleEndGame();
        Globals.app.setNumberOfRound(0);
    }
}