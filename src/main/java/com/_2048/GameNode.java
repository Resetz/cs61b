package com._2048;

public class GameNode {
    private int val;
    private final GameNode[] adj;

    public GameNode(int val) {
        this.val = val;
        adj = new GameNode[]{null, null, null, null};
    }
    public GameNode(int val, GameNode up, GameNode left, GameNode down, GameNode right) {
        this.val = val;
        adj = new GameNode[]{up, left, down, right};
    }

    public GameNode[] getAdj() {
        return adj;
    }

    public GameNode getAdj(int ind) {
        return adj[ind];
    }

    public void setAdj(GameNode[] adj) {
        System.arraycopy(adj, 0, this.adj, 0, 4);
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
