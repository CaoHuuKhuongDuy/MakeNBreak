package com.commons;


import java.util.Vector;
import com.models.User;

public class App {
    private Vector<User> users = new Vector<>();
    private int numberOfRound;
    private GameType gameType;

    public void setUsers(Vector <User> users) {
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

    public Vector <User> getUsers() {
        return this.users;
    }

    public int getNumberOfRound() {
        return this.numberOfRound;
    }

    public GameType getGameType() {
        return this.gameType;
    }
}
