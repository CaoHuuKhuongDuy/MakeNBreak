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
    private GridPane gridPane;
    private Pane mainLayout;
    private Coordinate gridPanePosition;

    public DraggingGamePlay(Node target) {
        this.target = target;
    }

    public DraggingGamePlay() {}

    public DraggingGamePlay setGridPanePosition(Coordinate gridPanePosition) {
        this.gridPanePosition = gridPanePosition;
        return this;
    }

    public DraggingGamePlay setTarget(Node target) {
        this.target = target;
        return this;
    }

    public DraggingGamePlay setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
        return this;
    }

    public DraggingGamePlay setMainLayout(Pane mainLayout) {
        this.mainLayout = mainLayout;
        return this;
    }

    public DraggingGamePlay setLayout(GridPane gridPane, Pane destinationLayout) {
        this.setGridPane(gridPane).setMainLayout(destinationLayout);
        return this;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("DraggingGamePlay");
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            // Store the offset between the mouse and the target's position
            offset = new double[]{event.getSceneX() - target.getLayoutX(), event.getSceneY() - target.getLayoutY()};
            dragging = true; // Start dragging
//            target.toFront();
//            gridPane.toFront();
//
//            // Find the ScrollPane if the target is inside one
            ScrollPane scrollPane = findScrollPane(target);
            if (scrollPane != null) {
                double hOffset = scrollPane.getHvalue() * (scrollPane.getContent().getBoundsInLocal().getWidth() - scrollPane.getViewportBounds().getWidth());
                double vOffset = scrollPane.getVvalue() * (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());
                offset[0] += hOffset;
                offset[1] += vOffset;

                // Move the block to the destination layout for dragging
                gridPane.getChildren().remove(target);
                mainLayout.getChildren().add(target);
                gridPane.requestLayout();
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {
            // Update the target's position as the mouse moves
            target.setLayoutX(event.getSceneX() - offset[0]);
            target.setLayoutY(event.getSceneY() - offset[1]);
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            // Stop dragging and decide where the block should go
            dragging = false;

//            this.mainLayout.getChildren().remove(target);
//            this.gridPane.add(target, gridPanePosition.x, gridPanePosition.y);
        }
    }

    // Helper method to find the GridPane in the parent hierarchy
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

    // Helper method to check if the target is inside the GridPane
//    private boolean isInsideGridPane(Node block) {
//        double blockX = block.getLayoutX();
//        double blockY = block.getLayoutY();
//
//        // Assuming rootLayout is the parent container of the GridPane
//        GridPane gridPane = findGridPane(block);
//        if (gridPane == null) {
//            return false;
//        }
//        for (Node child : rootLayout.getChildren()) {
//                double gridPaneX = gridPane.getLayoutX();
//                double gridPaneY = gridPane.getLayoutY();
//                double gridPaneWidth = gridPane.getWidth();
//                double gridPaneHeight = gridPane.getHeight();
//
//                // Check if the block's position is within the GridPane's bounds
//                if (blockX >= gridPaneX && blockX <= gridPaneX + gridPaneWidth &&
//                        blockY >= gridPaneY && blockY <= gridPaneY + gridPaneHeight) {
//                    return true;
//                }
//            }
//        return false;
//    }

}

//public class DraggingGamePlay implements EventHandler<MouseEvent> {
//    private Node target; // The node being dragged
//    private boolean dragging = false; // To distinguish between press and drag
//    private double[] offset; // The offset between the mouse and the target's position
//    private Pane rootLayout, destinationLayout;
//
//    public DraggingGamePlay(Node target) {
//        this.target = target;
//    }
//
//    public DraggingGamePlay() {}
//
//    public DraggingGamePlay setTarget(Node target) {
//        this.target = target;
//        return this;
//    }
//
//    public DraggingGamePlay setRootLayout(Pane rootLayout) {
//        this.rootLayout = rootLayout;
//        return this;
//    }
//
//    public DraggingGamePlay setDestinationLayout(Pane destinationLayout) {
//        this.destinationLayout = destinationLayout;
//        return this;
//    }
//
//    public DraggingGamePlay setLayout(Pane rootLayout, Pane destinationLayout) {
//        this.setRootLayout(rootLayout).setDestinationLayout(destinationLayout);
//        return this;
//    }
//
//    @Override
//    public void handle(MouseEvent event) {
//        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
//            // Store the offset between the mouse and the target's position
//            offset = new double[]{event.getSceneX() - target.getLayoutX(), event.getSceneY() - target.getLayoutY()};
//            dragging = true; // Start dragging
////            this.rootLayout.getChildren().remove(target);
////            this.destinationLayout.getChildren().add(target);
//        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {
//            // Update the target's position as the mouse moves
//            target.setLayoutX(event.getSceneX() - offset[0]);
//            target.setLayoutY(event.getSceneY() - offset[1]);
//        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
//            // Stop dragging
////            this.destinationLayout.getChildren().remove(target);
////            this.rootLayout.getChildren().add(target);
//            dragging = false;
//        }
//    }
//}