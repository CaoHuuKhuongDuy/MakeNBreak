/**
 * OOP Java Project  WiSe 2024/2025
 *
 * Purpose: This class manages a list of BuildingBlock objects, generates random building blocks, and constructs buildings using a grid-based approach.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.models.components;

// Represents a coordinate in the game grid
import com.commons.Coordinate;

// Provides utilities and shared resources for the game
import com.commons.Globals;

// Represents different game modes (e.g., single block vs. multiple block mode)
import com.commons.GameType;

// Provides support for working with colors in JavaFX
import javafx.scene.paint.Color;

// Utility classes for data structures and algorithms
import java.util.*;

public class ListBuildingBlock {
    private Vector<BuildingBlock> buildingBlocks;
    private int limitRow, limitCol;

    /**
     * Constructor initializes an empty list of building blocks.
     */
    public ListBuildingBlock() {
        this.buildingBlocks = new Vector<>();
    }

    /**
     * Retrieves the list of building blocks.
     *
     * @return Vector of BuildingBlock objects
     */
    public ListBuildingBlock(Vector<BuildingBlock> buildingBlocks) {
        this.buildingBlocks = buildingBlocks;
    }
    /**
     * Sets the list of building blocks.
     *
     * @param buildingBlocks A vector containing BuildingBlock objects
     */
    public void setBuildingBlocks(Vector <BuildingBlock> buildingBlocks) {
        this.buildingBlocks = buildingBlocks;
    }

    public Vector <BuildingBlock> getBuildingBlocks() {
        return this.buildingBlocks;
    }

    /**
     * Generates a specified number of random building blocks based on the game type.
     *
     * @param numberOfBuildingBlocks Number of blocks to generate
     * @param gameType The game mode (e.g., single or multiple block)
     * @return A vector containing the generated BuildingBlock objects
     */
    public Vector <BuildingBlock> generateRandomBuildingBlocks(int numberOfBuildingBlocks, GameType gameType) {
        Random random = new Random(System.currentTimeMillis());

        Vector <BuildingBlock> buildingBlocks = new Vector<>();
        if (numberOfBuildingBlocks == 0) {
            return buildingBlocks;
        }
        BuildingBlock randomBlock = Globals.buildingBlocks.get(random.nextInt(Globals.buildingBlocks.size())).clone();
        for (int i = 0; i < numberOfBuildingBlocks; i++) {
            if (gameType == GameType.MULTIPLE_BLOCK) {
                randomBlock = Globals.buildingBlocks.get(random.nextInt(Globals.buildingBlocks.size())).clone();
            } else {
                randomBlock = randomBlock.clone();
            }
            buildingBlocks.add(randomBlock);
            buildingBlocks.getLast().setColor(Globals.getRandomColor());
        }
        this.setBuildingBlocks(buildingBlocks);
        return buildingBlocks;
    }

    /**
     * Generates a grid-based building layout using randomly placed building blocks.
     *
     * @param row Number of rows in the grid
     * @param col Number of columns in the grid
     * @param low Minimum number of blocks to be placed
     * @param high Maximum number of blocks to be placed
     * @return A 2D array representing the building layout with colors assigned to blocks
     */
    public Color[][] generateBuilding(int row, int col, int low, int high) {
        this.limitRow = row;
        this.limitCol = col;
        Random random = new Random(System.currentTimeMillis());
        Color[][] building = new Color[row][col];

        int blockLimit = low + random.nextInt(high - low + 1);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                building[i][j] = Color.TRANSPARENT;
            }
        }
        PriorityQueue<Coordinate> q = new PriorityQueue<>();

        int maxX = -1;
        boolean[][] inqueue = new boolean[row][col];

        if (this.buildingBlocks.isEmpty()) {
            return building;
        }
        Collections.shuffle(this.buildingBlocks);
        Set <BuildingBlock> buildingBlocks = new HashSet<>(this.buildingBlocks);

        Vector <Coordinate> startingPoints = new Vector<>();
        for (int i = 0; i < col; i++) {
            startingPoints.add(new Coordinate(row - 1, i));
        }
        Collections.shuffle(startingPoints);

        int blocksPlaced = 0;

        for (Coordinate startingPoint : startingPoints) {
            q.add(startingPoint);
            boolean generated = false;
            while (!buildingBlocks.isEmpty() && !q.isEmpty()) {
                Coordinate candidateCell = q.poll();
                if (isCellOccupied(candidateCell, building)) {
                    continue;
                }

                Vector <BuildingBlock> removeBlocks = new Vector<>();
                for (BuildingBlock buildingBlock : buildingBlocks) {
                    Coordinate offset = new Coordinate();
                    int randomRotate = random.nextInt(4);
                    for (int i = 0; i < randomRotate; i++) {
                        buildingBlock.rotate();
                    }
                    if (random.nextBoolean()) {
                        buildingBlock.flip();
                    }
                    if (tryPlaceBlock(candidateCell, buildingBlock, building, offset)) {
                        generated = true;
                        blocksPlaced++;
                        for (Coordinate cell : buildingBlock.getCells()) {
                            Coordinate newPos = new Coordinate(cell.x - offset.x, cell.y - offset.y);
                            if (!isCellOccupied(new Coordinate(newPos.x - 1, newPos.y), building) && !inqueue[newPos.x - 1][newPos.y]) {
                                inqueue[newPos.x - 1][newPos.y] = true;
                                q.add(new Coordinate(newPos.x - 1, newPos.y));
                            }
                            maxX = Math.max(maxX, newPos.x);
                        }
                        for (Coordinate cell : buildingBlock.getCells()) {
                            Coordinate newPos = new Coordinate(cell.x - offset.x, cell.y - offset.y);
                            if (newPos.x != maxX) continue;
                            if (!isCellOccupied(new Coordinate(newPos.x, newPos.y - 1), building) && !inqueue[newPos.x][newPos.y - 1]) {
                                inqueue[newPos.x][newPos.y - 1] = true;
                                q.add(new Coordinate(newPos.x, newPos.y - 1));
                            }
                            if (!isCellOccupied(new Coordinate(newPos.x, newPos.y + 1), building) && !inqueue[newPos.x][newPos.y + 1]) {
                                inqueue[newPos.x][newPos.y + 1] = true;
                                q.add(new Coordinate(newPos.x, newPos.y + 1));
                            }
                        }
                        removeBlocks.add(buildingBlock);
                        break;
                    }
                }
                for (BuildingBlock removeBlock : removeBlocks) {
                    buildingBlocks.remove(removeBlock);
                }
                if (blocksPlaced >= blockLimit) {
                    break;
                }
            }
            if (generated  && blocksPlaced >= blockLimit) {
                break;
            }
        }

        return building;
    }

    /**
     * Checks if a given cell in the grid is occupied by a building block.
     *
     * @param cell The coordinate of the cell
     * @param building The 2D array representing the grid
     * @return True if the cell is occupied, false otherwise
     */
    private boolean isCellOccupied(Coordinate cell, Color[][] building) {
        if (cell.x < 0 || cell.x >= this.limitRow || cell.y < 0 || cell.y >= this.limitCol) {
            return true;
        }
        return building[cell.x][cell.y] != null && building[cell.x][cell.y] != Color.TRANSPARENT;
    }

    /**
     * Attempts to place a building block at a specified position on the grid.
     *
     * @param position The target position for the block
     * @param buildingBlock The block to be placed
     * @param building The 2D array representing the grid
     * @param resultOffset Stores the offset if the block is placed successfully
     * @return True if the block is placed successfully, false otherwise
     */
    private boolean tryPlaceBlock(Coordinate position, BuildingBlock buildingBlock, Color[][] building, Coordinate resultOffset) {
        Vector <Coordinate> cells = buildingBlock.getCells();
        Collections.shuffle(cells);
        for (Coordinate startCell : cells) {
            Coordinate offset = new Coordinate(startCell.x - position.x, startCell.y - position.y);
            boolean check = true;
            for (Coordinate cell: cells) {
                Coordinate newPos = new Coordinate(cell.x - offset.x, cell.y - offset.y);
                if (isCellOccupied(newPos, building)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                for (Coordinate cell: cells) {
                    resultOffset.x = offset.x;
                    resultOffset.y = offset.y;
                    Coordinate newPos = new Coordinate(cell.x - resultOffset.x, cell.y - resultOffset.y);
                    building[newPos.x][newPos.y] = buildingBlock.getColor();
                }
                return true;
            }
        }
        return false;
    }
}
