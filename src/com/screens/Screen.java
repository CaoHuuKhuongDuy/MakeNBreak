package com.screens;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Screen extends Pane {
    protected String title;
    protected Stage primaryStage;

    public Screen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.title = "";
    }

    public Screen(Stage primaryStage, String title) {
        this.primaryStage = primaryStage;
        this.title = title;
    }
    public abstract void display();
    public abstract void initHandlers();
}
