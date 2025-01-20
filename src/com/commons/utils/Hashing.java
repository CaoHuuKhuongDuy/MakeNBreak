package com.commons.utils;

import com.commons.Globals;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Hashing {
    private int[][] resultMatrix, compareMatrix;
    private int rowResultMatrix, colResultMatrix;
    private int rowCompareMatrix, colCompareMatrix;
    private int[][] hashResultMatrix, hashCompareMatrix;
    private final int base = 397;
    private int[] powBase;

    public Hashing() {
        powBase = new int[100];
        powBase[0] = 1;
        for (int i = 1; i < 100; i++) {
            powBase[i] = powBase[i - 1] * base;
        }
    }

    public Hashing setResultMatrix(Color[][] resultMatrix, int row, int col) {
        this.rowResultMatrix = row;
        this.colResultMatrix = col;
        this.resultMatrix = new int[row + 1][col + 1];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.resultMatrix[i + 1][j + 1] = hashColor(resultMatrix[i][j]);
            }
        }
        this.hashResultMatrix = hashMatrix(this.resultMatrix, row, col);
        return this;
    }

    public Hashing setCompareMatrix(Color[][] compareMatrix, int row, int col) {
        this.rowCompareMatrix = row;
        this.colCompareMatrix = col;
        this.compareMatrix = new int[row + 1][col + 1];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.compareMatrix[i + 1][j + 1] = hashColor(compareMatrix[i][j]);
            }
        }
        this.hashCompareMatrix = hashMatrix(this.compareMatrix, row, col);
        return this;
    }

    public boolean compare() {
        if (this.rowResultMatrix > this.rowCompareMatrix || this.colResultMatrix > this.colCompareMatrix) {
            return false;
        }
        for (int i = 1; i <= rowCompareMatrix; i++) {
            for (int j = 1; j <= colCompareMatrix; j++) {
                if (i + this.rowResultMatrix - 1 > this.rowCompareMatrix || j + this.colResultMatrix - 1 > this.colCompareMatrix) {
                    break;
                }
                boolean check = true;
                for (int k = i; k < i + this.rowResultMatrix; k++) {
                    if (getHash(this.hashCompareMatrix[k], j, j + this.colResultMatrix - 1) != getHash(this.hashResultMatrix[k - i + 1], 1, this.colResultMatrix)) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getHash(int[] matrix, int l, int r) {
        return matrix[r] - matrix[l - 1] * powBase[r - l + 1];
    }

    private int[][] hashMatrix(int[][] matrix, int row, int col) {
        int[][] hashMatrix = new int[row + 1][col + 1];
        for (int i = 1; i <= row; i++) {
            hashMatrix[i][0] = 0;
            for (int j = 1; j <= col; j++) {
                hashMatrix[i][j] = hashMatrix[i][j - 1] * base + matrix[i][j];
            }
        }
        return hashMatrix;
    }

    private int hashColor(Color color) {
        if (color.equals(Color.TRANSPARENT)) {
            return Globals.listColors.length;
        }
        return Arrays.asList(Globals.listColors).indexOf(color);
    }
}
