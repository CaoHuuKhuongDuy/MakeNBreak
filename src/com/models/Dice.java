package com.models;

import com.commons.Coordinate;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Dice extends Entity {
    private int value;
    private boolean rolling;

    public Dice() {
        super();
        this.value = 0;
        rolling = false;
    }

    public Dice(Coordinate position, double width, double height) {
        super(position, true, width, height);
        this.value = 0;
        rolling = false;
    }

    public void draw() {
        this.getChildren().clear();
        // Set initial image sizes
        Button diceButton = new Button();
        ImageView imageViewDiceButton = new ImageView(new Image("/resources/assets/images/diceButton.png"));
        imageViewDiceButton.setFitHeight(this.height);
        imageViewDiceButton.setFitWidth(this.width);
        diceButton.setGraphic(imageViewDiceButton);
        diceButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        diceButton.setPrefSize(this.width, this.height);

        diceButton.setOnMouseClicked(event -> {
            if (!rolling) {
                rolling = true;
                Thread thread = new Thread(() -> {
                    Random random = new Random();
                    while (rolling) {
                        int diceValue = random.nextInt(3) + 1; // Generate a random number between 1 and 3
                        String imagePath = String.format("/resources/assets/images/Dice%ds.png", diceValue);
                        imageViewDiceButton.setImage(new Image(imagePath)); // Update the image
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
            else rolling = false;

        });
        this.getChildren().add(diceButton);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
