package Sodoku;

import java.io.*;

public class Solution {
    //判断解答个数以及是否唯一
    private int solutionIndex = 0;
    // 数独的格子
    private final int[][] board = new int[9][9];
    //按照行、列、3x3九宫格分区
    private final Block[] rows = new Block[9];
    private final Block[] cols = new Block[9];
    private final Block[] squares = new Block[9];

    private final PrintStream out = System.out;

    public Solution(int[][] board) {
        //初始化棋盘，用来填入固定的数字
        init(board);
        //广度优先搜索
        search(0, 0);
    }

    private void init(int[][] board) {
        for (int i = 0; i < 9; i++) {
            rows[i] = new Block();
            cols[i] = new Block();
            squares[i] = new Block();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = board[i][j];
                if (board[i][j] != 0) {
                    fillIn(i, j, board[i][j], true);
                }
            }
        }
    }

    private void search(int row, int col) {

        if (col == 9) {
            search(row + 1, 0);
            return;
        }

        if (row == 9) {
            printBoard();
            return;
        }

        if (board[row][col] != 0) {
            search(row, col + 1);
            return;
        }

        for (int num = 1; num <= 9; num++) {
            if (isAvailable(row, col, num)) {
                board[row][col] = num;
                fillIn(row, col, num, true);
                search(row, col + 1);
                board[row][col] = 0;
                fillIn(row, col, num, false);
            }
        }


    }

    private void printBoard() {
        solutionIndex++;
        out.println("Solution #" + solutionIndex);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                out.print(board[i][j] + " ");
            }
            out.println();
        }
        out.println();
    }

    private static int getSquareIndex(int row, int col) {
        return row / 3 * 3 + col / 3;

    }


    private void fillIn(int row, int col, int num, boolean isUsed) {
        int squareIndex = getSquareIndex(row, col);
        rows[row].setUsed(num, isUsed);
        cols[col].setUsed(num, isUsed);
        squares[squareIndex].setUsed(num, isUsed);
    }

    private boolean isAvailable(int row, int col, int num) {
        int squareIndex = getSquareIndex(row, col);
        if (!rows[row].isEmpty(num)) {
            return false;
        }

        if (!cols[col].isEmpty(num)) {
            return false;
        }

        if (!squares[squareIndex].isEmpty(num)) {
            return false;
        }
        return true;
    }

    private static class Block {
        private boolean[] isUsed;

        private Block() {
            isUsed = new boolean[10];
        }

        private boolean isEmpty(int num) {
            return !isUsed[num];
        }

        private void setUsed(int num, boolean used) {
            isUsed[num] = used;
        }
    }
}


