package com.controllers.mouse;

import com.models.Clock;
import com.models.Dice;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RollingDice implements EventHandler<MouseEvent> {
    Dice dice;
    Clock clock;
    AtomicInteger diceValue;
    Runnable clockCallBack;
    GenerateCard generateCard;

    public RollingDice() {
        diceValue = new AtomicInteger();
    }

    public RollingDice setClockCallBack(Runnable clockCallBack) {
        this.clockCallBack = clockCallBack;
        return this;
    }

    public RollingDice setDependencies(Dice dice, Clock clock) {
        this.dice = dice;
        this.clock = clock;
        return this;
    }

    public RollingDice setDice(Dice dice) {
        this.dice = dice;
        return this;
    }

    public RollingDice setClock(Clock clock) {
        this.clock = clock;
        return this;
    }

    public RollingDice setGenerateCard(GenerateCard generateCard) {
        this.generateCard = generateCard;
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (!dice.isInteractable()) return;
            if (!dice.isRolling()) {
                dice.setRolling(true);
                Thread thread = new Thread(() -> {
                    Random random = new Random();
                    while (dice.isRolling()) {
                        diceValue.set(random.nextInt(3) + 1); // Generate a random number between 1 and 3
                        String imagePath = String.format("/resources/assets/images/Dice%ds.png", diceValue.get());
                        dice.setImageView(imagePath);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
            else {
                dice.setValue(diceValue.get());
                dice.setRolling(false);
                clock.setTime(dice.getValue() * 60);
                clock.startCounting(clockCallBack);
                dice.setInteractable(false);
                generateCard.run();
            }
        }
    }
}
