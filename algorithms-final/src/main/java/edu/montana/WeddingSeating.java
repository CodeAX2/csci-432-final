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
            // Select two random guests
            Person guestA = null, guestB = null;
            int guestATable = -1, guestASeat = -1, guestBTable = -1, guestBSeat = -1;
            while (guestA == null || guestB == null) {
                int tableNum = rand.nextInt(numTables);
                int seatNum = rand.nextInt(guestsPerTable);
                if (guestA == null) {
                    guestA = tables[tableNum][seatNum];
                    guestATable = tableNum;
                    guestASeat = seatNum;
                } else {
                    guestB = tables[tableNum][seatNum];
                    guestBTable = tableNum;
                    guestBSeat = seatNum;
                }
            }

            // Swap them
            swapGuests(guestATable, guestASeat, guestBTable, guestBSeat);

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

    public String toString() {
        String asString = "";

        for (int i = 0; i < numTables; i++) {
            asString += "Table " + (i + 1) + ":\n";
            for (Person guest : tables[i]) {
                asString += "\t" + guest + "\n";
            }
            if (i != numTables - 1) {
                asString += "\n";
            }
        }

        return asString;

    }

    public void setScorer(SeatingScorer scorer) {
        this.scorer = scorer;
    }

    public SeatingScorer getScorer() {
        return scorer;
    }

}
