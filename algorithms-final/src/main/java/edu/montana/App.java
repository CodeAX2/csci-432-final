package edu.montana;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {

        long totalRuntime = 0;
        int totalScore = 0;

        SeatingScorer scorer = new SimpleCountScorer();
        SeatingSolver solverA = new GreedySolver();
        SeatingSolver solverB = new BacktrackingSolver();

        for (int iter = 0; iter < 10; iter++) {

            List<Person> guests = new ArrayList<>();

            int numGuests = 8;
            int maxNumFriends = 3;
            int maxNumEnemies = 2;
            int guestsPerTable = 2;

            for (int i = 0; i < numGuests; i++) {
                Person cur = new Person();
                guests.add(cur);
            }

            Person.createRandomRelations(maxNumFriends, maxNumEnemies, guests);

            // List<Person> guests = loadGuestlist("Guests/XA2.txt");

            WeddingSeating seating = new WeddingSeating(guestsPerTable, guests, scorer);

            solverA.solveSeating(new WeddingSeating(seating));
            solverB.solveSeating(new WeddingSeating(seating));

            totalRuntime += solverA.getLastSolveRuntime();
            totalScore += solverB.getLastSolveScore() - solverA.getLastSolveScore();

            guests.clear();
            seating = null;

            System.out.println(iter);

        }

        System.out.println("Backtracking Runtime Avg: " + totalRuntime / 10.0 + "ms");
        System.out.println("Backtracking Score Avg: " + totalScore / 10.0 + " ("
                + scorer.getClass().getSimpleName() + ")");

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
