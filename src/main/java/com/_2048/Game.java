package com._2048;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    final static private int[] DI = {-1,0,1,0};
    final static private int[] DJ = {0,-1,0,1};

    private ArrayList<ArrayList<GameNode>> grid;
    int capacity = 0;
    private Random rand;
    boolean gameOver = false;

    private int score = 0;

    private final int SIZE;

    public Game(int SIZE) {
        this.SIZE = SIZE;
        grid = new ArrayList<>();
        rand = new Random();

        for (int i = 0; i < SIZE; i++) {
            grid.add(new ArrayList<GameNode>());
            for (int j = 0; j < SIZE; j++) grid.get(i).add(new GameNode(0));
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                GameNode[] adj = new GameNode[]{null, null, null, null};
                for (int k = 0 ; k < 4 ; k++) {
                    int ni = i + DI[k];
                    int nj = j + DJ[k];

                    if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE) {
                        adj[k] = grid.get(ni).get(nj);
                    }
                }
                grid.get(i).get(j).setAdj(adj);
            }
        }
    }

    public int addRandom() {
        if (capacity == SIZE * SIZE) {
            gameOver = true;
            return -1; // Lost the game?
        }

        int row = rand.nextInt(0,4);
        int col = rand.nextInt(0, 4);

        while (grid.get(row).get(col).getVal() != 0) {
            row = rand.nextInt(0,4);
            col = rand.nextInt(0, 4);
        }

        this.grid.get(row).get(col).setVal(1);
        capacity++;
        return 0;
    }
    public void parseMove(int type) {
        // BOARD MOVES
        // Down, Right, Up, Left
        // 0     1      2   3


        GameNode[] starts = new GameNode[4];

        if (type == 0)  {
            // Up - starts = bottom row

            for (int i = 0 ; i < SIZE ; i++) {
                starts[i] = grid.get(SIZE-1).get(i);
            }
        }
        if (type == 1) {
            // Left - starts = right row

            for (int i = 0 ; i < SIZE ; i++) {
                starts[i] = grid.get(i).get(SIZE-1);
            }
        }
        if (type == 2) {
            // Down - starts = top row

            for (int i = 0 ; i < SIZE ; i++) {
                starts[i] = grid.get(0).get(i);
            }
        }
        if (type == 3) {
            // Right - starts = top row

            for (int i = 0 ; i < SIZE ; i++) {
                starts[i] = grid.get(i).get(0);
            }
        }

        // Process each linked list using two pointers;

        for (int i = 0 ; i < SIZE ; i++) {
            GameNode left = starts[i];
            GameNode right = starts[i].getAdj(type);
            while (right != null) {
                if (right.getVal() != 0) {
                    if (left.getVal() == 0) {
                        // move whatever is in the right pointer to left pointer
                        if (right.getVal() != 0) {
                            left.setVal(right.getVal());
                            right.setVal(0);
                        }
                    } else if (left.getVal() == right.getVal()) {
                        // if what is in the right pointer is equal to the left
                        // pointer, merge.
                        left.setVal(left.getVal() + 1);
                        processScore(left.getVal());

                        right.setVal(0);
                        left = left.getAdj(type);
                        capacity--;

                    } else {
                        // if they are not equal, stack the right pointer against the left pointer without merging.
                        left = left.getAdj(type);
                        if (left != right) {
                            left.setVal(right.getVal());
                            right.setVal(0);
                        }
                    }
                }

                right = right.getAdj(type);
            }
        }
    }

    public int[][] getNumArr() {
        int[][] ret = new int[SIZE][SIZE];
        for (int i = 0 ; i < SIZE ; i++) {
            for (int j = 0 ; j < SIZE ; j++) {
                ret[i][j] = grid.get(i).get(j).getVal();
            }
        }
        return ret;
    }
    public void dumpArr() {
        int[][] arr = getNumArr();

        for (int i = 0 ; i < SIZE ; i++) {
            for (int j =0 ; j < SIZE ; j++) {
                System.out.printf("[%4s]", arr[i][j] == 1 ? "----" : Integer.toString(arr[i][j]));
            }
            System.out.println();
        }
    }

    public void processScore(int mergedCellValue) {
        // mergedCellValue = log_2(value of the merged cell)
        System.out.println("Earned score!: " + mergedCellValue);
        score += (1 << (mergedCellValue-1));
    }

    public ArrayList<ArrayList<GameNode>> getGrid() {
        return grid;
    }

    public int getScore() {
        return score;
    }

}
