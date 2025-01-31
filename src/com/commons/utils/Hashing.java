/**
 * OOP Java Project WiSe 2024/2025
 *
 * Purpose: This class provides a rolling hashing mechanism to compare 2D matrices of colors,
 *          which is useful for checking if a constructed shape in the game matches
 *          a target shape.
 *
 * @Hong Minh Dao
 * @Phan Khanh Linh Dang
 * @version 1.0
 */

package com.commons.utils;

// Imports global color definitions
import com.commons.Globals;
// Represents color values in JavaFX
import javafx.scene.paint.Color;

// Utility for working with arrays
import java.util.Arrays;

public class Hashing {
    // Matrices representing the hashed values of the game board and the player's construction
    private int[][] resultMatrix, compareMatrix;
    private int rowResultMatrix, colResultMatrix;
    private int rowCompareMatrix, colCompareMatrix;
    private int[][] hashResultMatrix, hashCompareMatrix;
    private final int base = 397; // Base value for hashing calculations
    private int[] powBase; // Precomputed power values of base

    /**
     * Constructor initializes the power base array to optimize hashing calculations.
     */
    public Hashing() {
        powBase = new int[100];
        powBase[0] = 1;
        for (int i = 1; i < 100; i++) {
            powBase[i] = powBase[i - 1] * base;
        }
    }

    /**
     * Sets the reference result matrix and computes its hash values.
     *
     * @param resultMatrix 2D array of colors representing the target structure.
     * @param row Number of rows in the matrix.
     * @param col Number of columns in the matrix.
     * @return The Hashing object for method chaining.
     */
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

    /**
     * Sets the comparison matrix (player's constructed shape) and computes its hash values.
     *
     * @param compareMatrix 2D array of colors representing the player's structure.
     * @param row Number of rows in the matrix.
     * @param col Number of columns in the matrix.
     * @return The Hashing object for method chaining.
     */
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

    /**
     * Compares the hashed matrices to check if the target shape exists within the player's shape.
     *
     * @return true if the player's structure contains the reference shape, otherwise false.
     */
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

    /**
     * Retrieves the hash value for a section of a row in a matrix.
     *
     * @param matrix Row of a matrix containing precomputed hash values.
     * @param l Start index.
     * @param r End index.
     * @return Computed hash value for the given row segment.
     */
    private int getHash(int[] matrix, int l, int r) {
        return matrix[r] - matrix[l - 1] * powBase[r - l + 1];
    }

    /**
     * Computes hash values for an entire matrix.
     *
     * @param matrix The input matrix containing integer color hashes.
     * @param row Number of rows.
     * @param col Number of columns.
     * @return A matrix containing precomputed hash values for quick lookup.
     */
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

    /**
     * Converts a color into a unique hash value for comparison.
     *
     * @param color The color to be converted.
     * @return An integer hash value representing the color.
     */
    private int hashColor(Color color) {
        if (color.equals(Color.TRANSPARENT)) {
            return Globals.listColors.length;
        }
        return Arrays.asList(Globals.listColors).indexOf(color);
    }
}
