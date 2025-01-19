package com.models.components;

import com.commons.Coordinate;

import java.util.Collections;
import java.util.Vector;
import java.util.PriorityQueue;
import java.util.Random;

import com.commons.GameType;
import com.commons.Globals;
import javafx.scene.paint.Color;
import java.util.Arrays;


public class ListBuildingBlock {
    private Vector <BuildingBlock> buildingBlocks;
    private int limitRow, limitCol;

    public ListBuildingBlock() {
        this.buildingBlocks = new Vector<>();
    }

    public ListBuildingBlock(Vector<BuildingBlock> buildingBlocks) {
        this.buildingBlocks = buildingBlocks;
    }

    public void addBuildingBlock(BuildingBlock buildingBlock) {
        this.buildingBlocks.add(buildingBlock);
    }

    public Vector <BuildingBlock> getBuildingBlocks() {
        return this.buildingBlocks;
    }

    public void setBuildingBlocks(Vector <BuildingBlock> buildingBlocks) {
        this.buildingBlocks = buildingBlocks;
    }

    public Vector <BuildingBlock> generateRandomBuildingBlocks(int numberOfBuildingBlocks) {
        Random random = new Random();

        Vector <BuildingBlock> buildingBlocks = new Vector<>();
        for (int i = 0; i < numberOfBuildingBlocks; i++) {
            BuildingBlock randomBlock = Globals.buildingBlocks.get(random.nextInt(Globals.buildingBlocks.size())).clone();
            buildingBlocks.add(randomBlock);
            buildingBlocks.getLast().setColor(Globals.getRandomColor());
        }
        this.setBuildingBlocks(buildingBlocks);
        return buildingBlocks;
    }


    public Color[][] generateBuilding(int row, int col, int numberBlock, GameType cardType) {
        this.limitRow = row;
        this.limitCol = col;
        Random random = new Random();
        Color[][] building = new Color[row][col];
        PriorityQueue<Coordinate> q = new PriorityQueue<>();

        int maxX = -1;
        boolean[][] inqueue = new boolean[row][col];

        if (this.buildingBlocks.isEmpty()) {
            return building;
        }
        Vector <BuildingBlock> buildingBlocks;
        if (cardType == GameType.SINGLE_BLOCK) {
            buildingBlocks = new Vector<>(Arrays.asList(this.buildingBlocks.get(random.nextInt(this.buildingBlocks.size()))));
        }
        else {
            buildingBlocks = this.buildingBlocks;
        }

        Vector <Coordinate> startingPoints = new Vector<>();
        for (int i = 0; i < col; i++) {
            startingPoints.add(new Coordinate(row - 1, i));
        }
        Collections.shuffle(startingPoints);
        for (Coordinate startingPoint : startingPoints) {
            q.add(startingPoint);
            boolean generated = false;
            while (numberBlock > 0 && !q.isEmpty()) {
                Coordinate candidateCell = q.poll();
                if (isCellOccupied(candidateCell, building)) {
                    continue;
                }

                Collections.shuffle(buildingBlocks);
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
                        numberBlock--;
                        generated = true;
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
                        break;
                    }
                }
            }
            if (generated) {
                break;
            }
        }

        return building;
    }

    private boolean isCellOccupied(Coordinate cell, Color[][] building) {
        if (cell.x < 0 || cell.x >= this.limitRow || cell.y < 0 || cell.y >= this.limitCol) {
            return true;
        }
        return building[cell.x][cell.y] != null && building[cell.x][cell.y] != Color.BLACK;
    }

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
