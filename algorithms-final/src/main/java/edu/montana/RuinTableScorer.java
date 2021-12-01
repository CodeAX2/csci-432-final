package edu.montana;

public class RuinTableScorer implements SeatingScorer {

    @Override
    public int scoreSeating(WeddingSeating seating) {

        int score = 0;

        Person[][] tables = seating.getAllTables();
        // Loop over all the tables
        for (Person[] table : tables) {
            boolean tableHasEnemies = false;
            int tableFriendScore = 0;
            // Loop over each pair of guests
            for (int i = 0; i < table.length; i++) {
                for (int j = i + 1; j < table.length; j++) {
                    Person personA = table[i];
                    Person personB = table[j];

                    // If either person is null, this is not a valid seating arrangement
                    // So we retrun int min
                    if (personA == null || personB == null)
                        return Integer.MIN_VALUE;

                    // If the pair are friends, increment the table friend score
                    if (personA.isFriend(personB)) {
                        tableFriendScore++;
                    }

                    // If the pair are enemies, table has enemies
                    if (personA.isEnemy(personB)) {
                        tableHasEnemies = true;
                    }
                }
            }
            if (!tableHasEnemies) {
                score += tableFriendScore;
            }
        }

        return score;
    }

}
