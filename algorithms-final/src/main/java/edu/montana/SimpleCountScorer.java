package edu.montana;

public class SimpleCountScorer implements SeatingScorer {

    public int scoreSeating(WeddingSeating seating) {

        int score = 0;

        Person[][] tables = seating.getAllTables();
        // Loop over all the tables
        for (Person[] table : tables) {
            // Loop over each pair of guests
            for (int i = 0; i < table.length; i++) {
                for (int j = i + 1; j < table.length; j++) {
                    Person personA = table[i];
                    Person personB = table[j];

                    // If either person is null, this is not a valid seating arrangement
                    // So we retrun int min
                    if (personA == null || personB == null)
                        return Integer.MIN_VALUE;

                    // If the pair are friends, increment the score
                    if (personA.isFriend(personB)) {
                        score++;
                    }

                    // If the pair are enemies, decrement the score
                    if (personA.isEnemy(personB)) {
                        score--;
                    }

                    // Interesting thing of note, we can incremend/decrement by any number and it
                    // does not change the final assignment, since the best assignment will still be
                    // the best
                }
            }
        }

        return score;
    }

}
