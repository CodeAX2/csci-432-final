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

                    // If the pair are friends, increment the score by 1
                    if (personA.isFriend(personB))
                        score++;

                    // If the pair are enemies, decrement the score by 1
                    if (personA.isEnemy(personB))
                        score--;
                }
            }
        }

        return score;
    }

}
