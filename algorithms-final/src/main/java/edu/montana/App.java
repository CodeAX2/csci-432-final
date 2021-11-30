package edu.montana;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        List<Person> guests = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Person cur = new Person();
            guests.add(cur);
        }
        Person.createRandomRelations(1, 1, guests);

        WeddingSeating seating = new WeddingSeating(2, guests, new SimpleCountScorer());

        WeddingSeating seatingCopy = new WeddingSeating(seating);
        SeatingSolver geneticSolver = new GeneticSolver();
        seatingCopy = geneticSolver.solveSeating(seatingCopy);

        SeatingSolver backtrackingSolver = new BacktrackingSolver();
        seatingCopy = new WeddingSeating(seating);
        seatingCopy = backtrackingSolver.solveSeating(seatingCopy);

        System.out.println("Genetic Algorithm Runtime: " + geneticSolver.getLastSolveRuntime() + "ms");
        System.out.println("Final Score: " + geneticSolver.getLastSolveScore());
        System.out.println();
        System.out.println("Backtracking Runtime: " + backtrackingSolver.getLastSolveRuntime() + "ms");
        System.out.println("Final Score: " + backtrackingSolver.getLastSolveScore());
    }
}
