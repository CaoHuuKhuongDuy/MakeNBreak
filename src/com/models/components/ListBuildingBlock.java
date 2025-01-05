package com.models.components;

import com.commons.Coordinate;

import java.util.Collections;
import java.util.Vector;
import java.util.PriorityQueue;
import java.util.Random;

import com.commons.Globals;
import com.models.CardType;
import javafx.scene.paint.Color;
import java.util.Arrays;


public class ListBuildingBlock {
    private Vector <BuildingBlock> buildingBlocks;
    private int limitRow, limitCol;

    public ListBuildingBlock() {
        Coordinate defaultPosition = new Coordinate(0, 0);
        Coordinate cell00 = new Coordinate(0, 0);
        Coordinate cell01 = new Coordinate(0, 1);
        Coordinate cell02 = new Coordinate(0, 2);
        Coordinate cell10 = new Coordinate(1, 0);
        Coordinate cell11 = new Coordinate(1, 1);
        Coordinate cell12 = new Coordinate(1, 2);
        Coordinate cell20 = new Coordinate(2, 0);
        Coordinate cell21 = new Coordinate(2, 1);
        Coordinate cell22 = new Coordinate(2, 2);

        BuildingBlock tBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11, cell12)));
        BuildingBlock smallZBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell21)));
        BuildingBlock oBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell01, cell10, cell11)));
        BuildingBlock bigLBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11)));
        BuildingBlock plusBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11, cell12, cell21)));
        BuildingBlock qBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell20, cell21)));
        BuildingBlock smallLBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11)));
        BuildingBlock wBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell21, cell22)));
        BuildingBlock caretBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell01, cell02, cell10, cell20)));
        BuildingBlock bigZBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell12, cell22)));
        BuildingBlock IBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell01, cell02)));

        this.buildingBlocks = new Vector<>(Arrays.asList(tBlock, smallZBlock, oBlock, bigLBlock, plusBlock, qBlock, smallLBlock, wBlock, bigZBlock, caretBlock, IBlock));
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


    public Color[][] generateBuilding(int row, int col, int numberBlock, CardType cardType) {
        this.limitRow = row;
        this.limitCol = col;
        Random random = new Random();
        Color[][] building = new Color[row][col];
        PriorityQueue<Coordinate> q = new PriorityQueue<>();
        q.add(new Coordinate(row - 1, random.nextInt(col)));
        int maxX = -1;
        boolean[][] inqueue = new boolean[row][col];

        if (cardType == CardType.SINGLE) {
            BuildingBlock singleBlock = null;
            singleBlock = this.buildingBlocks.get(random.nextInt(this.buildingBlocks.size()));

            while (numberBlock > 0 && !q.isEmpty()) {
                Coordinate candidateCell = q.poll();
                if (isCellOccupied(candidateCell, building)) {
                    continue;
                }

                Coordinate offset = new Coordinate();
                if (tryPlaceBlock(candidateCell, singleBlock, building, offset)) {
                    numberBlock--;
                    for (Coordinate cell : singleBlock.getCells()) {
                        Coordinate newPos = new Coordinate(cell.x - offset.x, cell.y - offset.y);
                        if (!isCellOccupied(new Coordinate(newPos.x - 1, newPos.y), building) && !inqueue[newPos.x - 1][newPos.y]) {
                            inqueue[newPos.x - 1][newPos.y] = true;
                            q.add(new Coordinate(newPos.x - 1, newPos.y));
                        }
                        maxX = Math.max(maxX, newPos.x);
                    }
                    for (Coordinate cell : singleBlock.getCells()) {
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

                }

            }

        }

        else {
            while (numberBlock > 0 && !q.isEmpty()) {
                Coordinate candidateCell = q.poll();
                if (isCellOccupied(candidateCell, building)) {
                    continue;
                }

                Collections.shuffle(this.buildingBlocks);
                for (BuildingBlock buildingBlock : this.buildingBlocks) {
                    Coordinate offset = new Coordinate();
                    if (tryPlaceBlock(candidateCell, buildingBlock, building, offset)) {
                        numberBlock--;
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
                Color color = Globals.getRandomColor();
                for (Coordinate cell: cells) {
                    resultOffset.x = offset.x;
                    resultOffset.y = offset.y;
                    Coordinate newPos = new Coordinate(cell.x - resultOffset.x, cell.y - resultOffset.y);
                    building[newPos.x][newPos.y] = color;
                }
                return true;
            }
        }
        return false;
    }
}
