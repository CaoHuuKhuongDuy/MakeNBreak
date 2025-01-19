package com.controllers.mouse;

import com.commons.Coordinate;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class DraggingGamePlay implements EventHandler<MouseEvent> {
    private Node target; // The node being dragged
    private boolean dragging = false; // To distinguish between press and drag
    private double[] offset; // The offset between the mouse and the target's position
    private Pane gridLayout, mainLayout;

    public DraggingGamePlay(Node target) {
        this.target = target;
    }

    public DraggingGamePlay() {}

    public DraggingGamePlay setTarget(Node target) {
        this.target = target;
        return this;
    }

    public DraggingGamePlay setGridLayout(Pane gridLayout) {
        this.gridLayout = gridLayout;
        return this;
    }

    public DraggingGamePlay setMainLayout(Pane setMainLayout) {
        this.mainLayout = setMainLayout;
        return this;
    }

    public DraggingGamePlay setLayout(Pane gridLayout, Pane mainLayout) {
        this.setGridLayout(gridLayout).setMainLayout(mainLayout);
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            // Store the offset between the mouse and the target's position
            offset = new double[]{event.getSceneX() - target.getLayoutX(), event.getSceneY() - target.getLayoutY()};
            dragging = true;
            ScrollPane scrollPane = findScrollPane(target);
            if (scrollPane != null) {
                double hOffset = scrollPane.getHvalue() * (scrollPane.getContent().getBoundsInLocal().getWidth() - scrollPane.getViewportBounds().getWidth());
                double vOffset = scrollPane.getVvalue() * (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());
                offset[0] += hOffset;
                offset[1] += vOffset;

                // Move the block to the destination layout for dragging
                gridLayout.getChildren().remove(target);
                mainLayout.getChildren().add(target);
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {
            target.setLayoutX(event.getSceneX() - offset[0]);
            target.setLayoutY(event.getSceneY() - offset[1]);
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            this.mainLayout.getChildren().remove(target);
            this.gridLayout.getChildren().add(target);
            dragging = false;
        }
    }

    private ScrollPane findScrollPane(Node node) {
        Node current = node;
        while (current != null) {
            if (current instanceof ScrollPane) {
                return (ScrollPane) current;
            }
            current = current.getParent();
        }
        return null;
    }
}