package edu.montana;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    // Copy constructor
    public WeddingSeating(WeddingSeating other) {
        this.numTables = other.numTables;
        this.guestsPerTable = other.guestsPerTable;

        this.unassignedGuests = new ArrayList<>(other.unassignedGuests);

        this.tables = new Person[numTables][guestsPerTable];
        for (int i = 0; i < numTables; i++) {
            for (int j = 0; j < guestsPerTable; j++) {
                this.tables[i][j] = other.tables[i][j];
            }
        }

        this.scorer = other.scorer;
    }

    public int getNumTables() {
        return numTables;
    }

    public int getNumSeatsPerTable() {
        return guestsPerTable;
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

    public void randomChange(int guestsToChange) {

        Random rand = new Random();

        for (int i = 0; i < guestsToChange; i++) {
            // Select random non-empty table
            int tableNum = rand.nextInt(tables.length);
            while (getNumOpenSeats(tableNum) == guestsPerTable) {
                tableNum = rand.nextInt(tables.length);
            }
            // Select random guest
            int guestIndex = rand.nextInt(guestsPerTable);
            while (tables[tableNum][guestIndex] == null) {
                guestIndex = rand.nextInt(guestsPerTable);
            }
            Person guest = tables[tableNum][guestIndex];
            tables[tableNum][guestIndex] = null;
            unassignedGuests.add(guest);

            // Select a new non-full table
            tableNum = rand.nextInt(tables.length);
            while (getNumOpenSeats(tableNum) == 0) {
                tableNum = rand.nextInt(tables.length);
            }

            assignTable(guest, tableNum);

        }

    }

    public void swapGuests(int guestATable, int guestASeat, int guestBTable, int guestBSeat) {
        Person personA = tables[guestATable][guestASeat];
        Person personB = tables[guestBTable][guestBSeat];

        tables[guestATable][guestASeat] = personB;
        tables[guestBTable][guestBSeat] = personA;

    }

    public int[] getTableSeatOfGuest(Person guest) {
        if (guest == null)
            return new int[] { -1, -1 };
        for (int i = 0; i < numTables; i++) {
            for (int j = 0; j < guestsPerTable; j++) {
                if (tables[i][j] == guest)
                    return new int[] { i, j };
            }
        }
        return new int[] { -1, -1 };
    }

    public Person getPersonAtSeat(int tableNum, int seatNum) {
        return tables[tableNum][seatNum];
    }

}
