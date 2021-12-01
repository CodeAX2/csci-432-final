package edu.montana;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticSolver extends SeatingSolver {

    @Override
    public WeddingSeating solveSeating(WeddingSeating seating) {

        long start = System.currentTimeMillis();

        // Must be an integer divisible by 2
        int populationSize = 300;

        // Create the population
        WeddingSeating[] population = new WeddingSeating[populationSize];
        int[] fitnessScores = new int[populationSize];

        for (int i = 0; i < populationSize; i++) {
            WeddingSeating curSeating = new WeddingSeating(seating);
            populateSeats(curSeating);

            population[i] = curSeating;
            fitnessScores[i] = curSeating.getCurrentScore();

        }

        for (int i = 0; i < 20000; i++) {

            WeddingSeating[] newPopulation = new WeddingSeating[populationSize];

            int[] newFitnessScores = new int[populationSize];

            // Generate the new population
            for (int j = 0; j < populationSize / 2; j++) {

                WeddingSeating[] parents = tournament(population, fitnessScores);

                WeddingSeating[] children = crossover(parents[0], parents[1]);

                // Cause mutations
                children[0].randomChange(3);
                children[1].randomChange(3);

                // Determine the children's score
                int child1Score = children[0].getCurrentScore();
                int child2Score = children[1].getCurrentScore();

                // Put the children into the new population
                newPopulation[j * 2] = children[0];
                newFitnessScores[j * 2] = child1Score;

                newPopulation[j * 2 + 1] = children[1];
                newFitnessScores[j * 2 + 1] = child2Score;

            }

            // Replace the old population and scores
            population = newPopulation;
            fitnessScores = newFitnessScores;

        }

        WeddingSeating bestSeating = null;
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < populationSize; i++) {
            int score = fitnessScores[i];
            if (bestSeating == null || score > bestScore) {
                bestSeating = population[i];
                bestScore = score;
            }
        }

        long end = System.currentTimeMillis();
        lastSolveRuntime = end - start;
        lastSolveScore = bestSeating.getCurrentScore();

        return bestSeating;
    }

    // Populates all the seats randomly
    private void populateSeats(WeddingSeating seating) {
        Random rand = new Random();

        List<Person> unassignedGuests = new ArrayList<>(seating.getUnassignedGuests());
        for (Person guest : unassignedGuests) {
            boolean assigned = false;
            while (!assigned) {
                int tableNum = rand.nextInt(seating.getNumTables());
                if (seating.getNumOpenSeats(tableNum) != 0) {
                    seating.assignTable(guest, tableNum);
                    assigned = true;
                }
            }
        }
    }

    // Selects 2 seats from the population using a tournament
    private WeddingSeating[] tournament(WeddingSeating[] population, int[] fitnessScores) {

        final double precent = 0.5;
        int amountToChoose = (int) (precent * population.length);

        Random rand = new Random();

        // The indices of the population that are in the tournament
        int[] popIndices = new int[amountToChoose];

        // Randomly select participants
        for (int i = 0; i < amountToChoose; i++) {
            popIndices[i] = rand.nextInt(population.length);
        }

        // Run the tournament, i.e. select the 2 with the best fitness
        // Fitness is the number of conflicts, so lower is better
        int bestIndex = 0, secondBestIndex = 1;
        WeddingSeating[] winners = new WeddingSeating[2];

        for (int i = 2; i < amountToChoose; i++) {

            if (fitnessScores[popIndices[i]] > fitnessScores[bestIndex]) {

                secondBestIndex = bestIndex;
                bestIndex = i;

            } else if (fitnessScores[popIndices[i]] > fitnessScores[secondBestIndex]) {
                secondBestIndex = i;
            }

        }

        winners[0] = population[bestIndex];
        winners[1] = population[secondBestIndex];

        return winners;

    }

    // Crosses over random seats from parentA and parentB.
    // Returns an array of length 2 containing the two resulting children
    private WeddingSeating[] crossover(WeddingSeating parentA, WeddingSeating parentB) {
        WeddingSeating[] children = new WeddingSeating[2];

        // Create children as copies of their parents
        WeddingSeating childA = new WeddingSeating(parentA);
        WeddingSeating childB = new WeddingSeating(parentB);

        Random rand = new Random();

        int tableNumPoint = rand.nextInt(parentA.getNumTables());
        for (int tableNum = tableNumPoint; tableNum < parentA.getNumTables(); tableNum++) {
            for (int seatNum = 0; seatNum < parentA.getNumSeatsPerTable(); seatNum++) {
                Person parentAPerson = parentA.getPersonAtSeat(tableNum, seatNum);
                Person parentBPerson = parentB.getPersonAtSeat(tableNum, seatNum);

                // Swap the positions
                if (parentAPerson != null) {
                    int[] locationInB = parentB.getTableSeatOfGuest(parentAPerson);
                    childA.swapGuests(locationInB[0], locationInB[1], tableNum, seatNum);
                }

                if (parentBPerson != null) {
                    int[] locationInA = parentA.getTableSeatOfGuest(parentBPerson);
                    childB.swapGuests(locationInA[0], locationInA[1], tableNum, seatNum);
                }

            }
        }

        children[0] = childA;
        children[1] = childB;

        return children;
    }

}
