package edu.montana;

import java.util.ArrayList;
import java.util.List;

public class WeddingSeating {

    private int numTables;
    private int guestsPerTable;

    private List<Person> unassignedGuests;

    private Person[][] tables;

    private SeatingScorer scorer;

    public WeddingSeating(int guestsPerTable, List<Person> guests, SeatingScorer scorer) {
        this.numTables = (int) Math.ceil((double) guests.size() / guestsPerTable);
        this.guestsPerTable = guestsPerTable;

        unassignedGuests = new ArrayList<>(guests);
        tables = new Person[numTables][guestsPerTable];

        this.scorer = scorer;
    }

    public int getNumTables() {
        return numTables;
    }

    public void assignTable(Person guest, int tableNum) {
        if (!unassignedGuests.contains(guest)) { // Check the guest is unassigned
            return;
        }

        Person[] table = tables[tableNum];
        boolean assigned = false;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                table[i] = guest;
                assigned = true;
                break;
            }
        }

        if (assigned)
            unassignedGuests.remove(guest);

    }

    public List<Person> getUnassignedGuests() {
        return unassignedGuests;
    }

    public int getNumOpenSeats(int tableNum) {
        int numOpen = 0;
        Person[] table = tables[tableNum];
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                numOpen++;
            }
        }
        return numOpen;
    }

    public Person[][] getAllTables() {
        return tables;
    }

    public int getCurrentScore() {
        return scorer.scoreSeating(this);
    }

}
