package edu.montana;

public class MinMaxScorer implements SeatingScorer {

    @Override
    public int scoreSeating(WeddingSeating seating) {
        int score = 0;

        Person[][] tables = seating.getAllTables();
        // Loop over all the tables
        for (Person[] table : tables) {
            int minFriends = Integer.MAX_VALUE;
            int maxEnemies = Integer.MIN_VALUE;
            // Loop over each guest
            for (int i = 0; i < table.length; i++) {

                // Guest is null, incomplete seating
                if (table[i] == null)
                    return Integer.MIN_VALUE;

                int numFriends = 0;
                int numEnemies = 0;

                // Count their number of friends and enemies at the table
                for (int j = 0; j < table.length; j++) {
                    if (j == i)
                        continue;
                    if (table[i].isFriend(table[j]))
                        numFriends++;
                    if (table[i].isEnemy(table[j]))
                        numEnemies++;
                }

                if (numFriends < minFriends)
                    minFriends = numFriends;

                if (numEnemies > maxEnemies)
                    maxEnemies = numEnemies;

            }
            score += minFriends - maxEnemies;
        }

        return score;
    }

}
