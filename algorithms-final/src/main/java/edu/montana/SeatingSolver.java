package edu.montana;

public abstract class SeatingSolver {
    protected int lastSolveScore;
    protected long lastSolveRuntime;

    public abstract void solveSeating(WeddingSeating seating);

    public int getLastSolveScore() {
        return lastSolveScore;
    }

    public long getLastSolveRuntime() {
        return lastSolveRuntime;
    }
}
