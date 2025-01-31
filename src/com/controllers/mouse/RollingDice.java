/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: This class handles the rolling dice interaction in the game.
 * It listens for mouse clicks to trigger dice rolling, determines a random dice value,
 * updates the dice image, and starts the game timer based on the rolled value.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.controllers.mouse;

// Import necessary classes for managing game elements
import com.models.Clock; // Manages the game timer
import com.models.Dice; // Represents the dice object and its state
import javafx.event.EventHandler; // Handles event-driven programming for JavaFX
import javafx.scene.input.MouseEvent; // Detects mouse interaction

import java.util.Random; // Generates random numbers for dice rolls
import java.util.concurrent.atomic.AtomicInteger; // Ensures thread-safe updates for the dice value

/**
 * The RollingDice class is responsible for handling dice rolling interactions in the game.
 * It listens for mouse clicks, generates a random dice value, updates the dice image,
 * and starts the game timer based on the rolled value.
 */
public class RollingDice implements EventHandler<MouseEvent> {
    Dice dice; // The dice object being rolled
    Clock clock; // The game timer
    AtomicInteger diceValue; // Stores the value of the rolled dice in a thread-safe manner
    Runnable clockCallBack; // Callback function triggered when the timer starts
    GenerateCard generateCard; // Manages card generation upon dice roll completion

    /**
     * Default constructor initializing the dice value.
     */
    public RollingDice() {
        diceValue = new AtomicInteger();
    }

    /**
     * Sets the callback function to be executed when the timer starts.
     * @param clockCallBack The callback function to execute.
     * @return The RollingDice instance for method chaining.
     */
    public RollingDice setClockCallBack(Runnable clockCallBack) {
        this.clockCallBack = clockCallBack;
        return this;
    }

    /**
     * Sets the dependencies for the dice and clock.
     * @param dice The dice object to be used.
     * @param clock The clock object managing time.
     * @return The RollingDice instance for method chaining.
     */
    public RollingDice setDependencies(Dice dice, Clock clock) {
        this.dice = dice;
        this.clock = clock;
        return this;
    }

    /**
     * Sets the GenerateCard instance for handling card generation.
     * @param generateCard The GenerateCard instance.
     * @return The RollingDice instance for method chaining.
     */
    public RollingDice setGenerateCard(GenerateCard generateCard) {
        this.generateCard = generateCard;
        return this;
    }

    /**
     * Handles mouse click events to trigger dice rolling.
     * It initiates a new thread to randomly roll the dice until stopped,
     * then assigns the rolled value, updates the clock, and generates a card.
     * @param event The mouse event that triggers the dice roll.
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (!dice.isInteractable()) return; // Ensure the dice is interactable before rolling

            if (!dice.isRolling()) {
                dice.setRolling(true);
                Thread thread = new Thread(() -> {
                    Random random = new Random();
                    while (dice.isRolling()) {
                        diceValue.set(random.nextInt(3) + 1); // Generate a random number between 1 and 3
                        String imagePath = String.format("/resources/assets/images/Dice%ds.png", diceValue.get());
                        dice.setImageView(imagePath); // Update the dice image
                        try {
                            Thread.sleep(10); // Simulate rolling effect
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            } else {
                dice.setValue(diceValue.get()); // Set the final rolled value
                dice.setRolling(false);
                clock.setTime(dice.getValue() * 60); // Set game time based on dice roll
                clock.startCounting(clockCallBack); // Start the game timer
                dice.setInteractable(false); // Prevent further interaction until next round
                generateCard.run(true); // Generate a new building card
            }
        }
    }
}
