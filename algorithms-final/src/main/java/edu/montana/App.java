package edu.montana;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {

        // List<Person> guests = new ArrayList<>();

        // for (int i = 0; i < 100; i++) {
        // Person cur = new Person();
        // guests.add(cur);
        // }
        // Person.createRandomRelations(5, 3, guests);

        List<Person> guests = loadGuestlist("Guests/XA2.txt");

        WeddingSeating seating = new WeddingSeating(5, guests, new RuinTableScorer());

        WeddingSeating seatingCopy = new WeddingSeating(seating);
        SeatingSolver geneticSolver = new GeneticSolver();
        seatingCopy = geneticSolver.solveSeating(seatingCopy);

        System.out.println("Genetic Algorithm Runtime: " + geneticSolver.getLastSolveRuntime() + "ms");
        System.out.println("Final Score: " + geneticSolver.getLastSolveScore());
        System.out.println(seatingCopy);

        System.out.println("\n\n");

        seatingCopy = new WeddingSeating(seating);
        SeatingSolver greedySolver = new GreedySolver();
        seatingCopy = greedySolver.solveSeating(seatingCopy);

        System.out.println("Greedy Algorithm Runtime: " + greedySolver.getLastSolveRuntime() + "ms");
        System.out.println("Final Score: " + greedySolver.getLastSolveScore());
        System.out.println(seatingCopy);

    }

    public static List<Person> loadGuestlist(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<Person> guests = new ArrayList<>();
            HashMap<String, Person> guestsMap = new HashMap<>();

            String curLine = br.readLine();

            int numGuests, numRelations;
            numGuests = Integer.parseInt(curLine.split(" ")[0]);
            numRelations = Integer.parseInt(curLine.split(" ")[1]);

            for (int i = 0; i < numGuests; i++) {
                Person p = new Person(br.readLine());
                guestsMap.put(p.getName(), p);
                guests.add(p);
            }

            for (int i = 0; i < numRelations; i++) {
                String[] tokens = br.readLine().split(" ");
                Person personA = guestsMap.get(tokens[0]);
                Person personB = guestsMap.get(tokens[2]);
                if (tokens[1].equals("F")) {
                    personA.addFriend(personB);
                } else if (tokens[1].equals("E")) {
                    personA.addEnemy(personB);
                }
            }

            br.close();
            return guests;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
