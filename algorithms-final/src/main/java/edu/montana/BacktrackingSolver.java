package edu.montana;

import java.util.List;

public class BacktrackingSolver extends SeatingSolver {

    @Override
    public WeddingSeating solveSeating(WeddingSeating seating) {
        long start = System.currentTimeMillis();

        seating = backtrackSolve(seating);

        long end = System.currentTimeMillis();

        lastSolveRuntime = end - start;
        lastSolveScore = seating.getCurrentScore();

        return seating;
    }


    // Runtime is OMEGA((n!)^2)
    private WeddingSeating backtrackSolve(WeddingSeating seating) {
        List<Person> unassignedGuests = seating.getUnassignedGuests();
        WeddingSeating optimalSeating = seating;
        int optimalScore = Integer.MIN_VALUE;
        for (Person guest : unassignedGuests) {
            for (int tableNum = 0; tableNum < seating.getNumTables(); tableNum++) {
                if (seating.getNumOpenSeats(tableNum) == 0)
                    continue;
                WeddingSeating newSeating = new WeddingSeating(seating);
                newSeating.assignTable(guest, tableNum);
                newSeating = backtrackSolve(newSeating);
                int newScore = newSeating.getCurrentScore();
                if (newScore > optimalScore) {
                    optimalScore = newScore;
                    optimalSeating = newSeating;
                }
            }
        }

        return optimalSeating;
    }

}
