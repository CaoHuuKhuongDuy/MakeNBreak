/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: Manages the application's state, including users, rounds, and game type.
 * Acts as a central hub for managing game-related data.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */
package com.commons;

import java.util.Vector; // A dynamic array implementation.
import com.models.User; // Represents a user in the application.

public class App {
    private Vector<User> users = new Vector<>(); // The list of users in the application.
    private int numberOfRound; // The number of rounds in the game.
    private GameType gameType; // The type of game being played.

    public void setUsers(Vector<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void setNumberOfRound(int numberOfRound) {
        this.numberOfRound = numberOfRound;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Vector<User> getUsers() {
        return this.users;
    }

    public int getNumberOfRound() {
        return this.numberOfRound;
    }

    public GameType getGameType() {
        return this.gameType;
    }
}