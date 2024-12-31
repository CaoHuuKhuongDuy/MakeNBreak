package com.screens;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Screen {
    protected String title;
    protected final Pane layout;
    protected Stage primaryStage;

    public Screen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.title = "";
        this.layout = new Pane();
    }

    public Screen(Stage primaryStage, String title) {
        this.primaryStage = primaryStage;
        this.title = title;
        this.layout = new VBox();
    }
    public abstract void display();
    public abstract void initHandlers();
}
