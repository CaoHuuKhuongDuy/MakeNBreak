package com;

import com.commons.Globals;
import com.screens.IntroScreen;
import com.screens.Screen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label dummyLabel = new Label("Loading...");
        VBox dummyLayout = new VBox(10, dummyLabel);
        Scene dummyScene = new Scene(dummyLayout);
        primaryStage.setScene(dummyScene);
        Globals.init();
        Globals.setDefaultResolution(1024, 768);

        Screen mainScreen = new IntroScreen(primaryStage);
        mainScreen.display();
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setTitle("Make N Break");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}