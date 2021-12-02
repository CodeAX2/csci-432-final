package edu.montana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GreedySolver extends SeatingSolver {

    @Override
    public WeddingSeating solveSeating(WeddingSeating seating) {
        long start = System.currentTimeMillis();

        seating = greedySolve(seating);

        long end = System.currentTimeMillis();

        lastSolveRuntime = end - start;
        lastSolveScore = seating.getCurrentScore();

        return seating;
    }

    private WeddingSeating greedySolve(WeddingSeating seating) {
        ArrayList<Person> unassignedGuests = new ArrayList<Person>(seating.getUnassignedGuests());

        // Sorts by most enemies to least
        Collections.sort(unassignedGuests, new EnemyCompare());

        for (int i = 0; i < seating.getNumTables(); i++) {
            // list of enemies of the table
            ArrayList<Person> tableEnemies = new ArrayList<Person>();
            Person prev = null;

            int n = unassignedGuests.size();

            for (int j = 0; j < n; j++) {
                boolean leave = false;

                // if the table is full
                if (seating.getNumOpenSeats(i) == 0) {
                    break;
                }

                // Makes sure prev isnt null
                if (prev == null) {

                } else if (prev.getFriends().size() > 0) {
                    // goes through all of the friends of the member, sorts by number of enemies
                    Collections.sort(prev.getFriends(), new EnemyCompare());
                    // chooses the friend with the most enemies and no conflicts and adds them to
                    // the table
                    for (Person friend : prev.getFriends()) {
                        if (unassignedGuests.contains(friend) && !tableEnemies.contains(friend)) {
                            seating.assignTable(friend, i);
                            unassignedGuests.remove(friend);
                            j -= 1;
                            leave = true;
                            break;
                        }

                    }
                } // If the above condition passed
                if (leave) {
                    continue;
                }
                // if not, then check if the person with the most enemies is an enemy of the
                // table
                if (tableEnemies.contains(unassignedGuests.get(j))) {
                    continue;
                }

                // if not, then choose them and add their enemies to the enemies of the table
                else {
                    seating.assignTable(unassignedGuests.get(j), i);
                    prev = unassignedGuests.get(j);
                    for (Person enemy : unassignedGuests.get(j).getEnemies()) {
                        if (!tableEnemies.contains(enemy)) {
                            tableEnemies.add(enemy);
                        }
                    }
                    unassignedGuests.remove(j);
                    j -= 1;
                }
            }
            // If some seats didnt get filled in, then add people with most enemies left to
            // that table.
            while (seating.getNumOpenSeats(i) > 0) {
                seating.assignTable(unassignedGuests.get(0), i);
                unassignedGuests.remove(0);
            }
        }

        WeddingSeating optimalSeating = seating;

        return optimalSeating;
    }

    // Allows us to compare person objects and compare enemies.
    class EnemyCompare implements Comparator<Person> {
        public int compare(Person c1, Person c2) {
            if (c1.getEnemies().size() == c2.getEnemies().size())
                return 0;
            else if (c1.getEnemies().size() > c2.getEnemies().size())
                return -1;
            else
                return 1;
        }
    }

}
