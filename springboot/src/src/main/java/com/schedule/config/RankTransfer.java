package com.schedule.config;

public class RankTransfer {
        public static Object[][] transpose(Object[][] matrix) {
            int numRows = matrix.length;
            int numCols = 0;
            for (int i = 0; i < numRows; i++) {
                numCols = Math.max(numCols, matrix[i].length);
            }
            Object[][] transposeMatrix = new Object[numCols][numRows];
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    transposeMatrix[j][i] = matrix[i][j];
                }
            }
            return transposeMatrix;
        }
    }

