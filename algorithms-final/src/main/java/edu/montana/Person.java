package edu.montana;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person {

    private static int idCounter = 0;

    private List<Person> friends;
    private List<Person> enemies;

    private String name;
    private int id;

    public Person(String name) {
        this.name = name;

        friends = new ArrayList<>();
        enemies = new ArrayList<>();

        id = idCounter++;
    }

    public Person() {
        friends = new ArrayList<>();
        enemies = new ArrayList<>();

        id = idCounter++;
        name = "Person " + id;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Person> getFriends() {
        return friends;
    }

    public List<Person> getEnemies() {
        return enemies;
    }

    public boolean isFriend(Person person) {
        return friends.contains(person);
    }

    public boolean isEnemy(Person person) {
        return enemies.contains(person);
    }

    public void addFriend(Person newFriend) {
        friends.add(newFriend);
        newFriend.friends.add(this);
    }

    public void addEnemy(Person newEnemy) {
        enemies.add(newEnemy);
        newEnemy.enemies.add(this);
    }

    // Creates random relations between each person
    public static void createRandomRelations(int maxNumFriends, int maxNumEnemies, List<Person> pool) {
        Random rand = new Random();

        for (Person p : pool) {
            int numFriendsToAdd = rand.nextInt(maxNumFriends + 1) - p.getFriends().size();
            int numEnemiesToAdd = rand.nextInt(maxNumEnemies + 1) - p.getEnemies().size();

            while (numFriendsToAdd > 0) {
                Person possibleFriend = pool.get(rand.nextInt(pool.size()));
                if (!p.isFriend(possibleFriend) && !p.isEnemy(possibleFriend) && p != possibleFriend
                        && possibleFriend.getFriends().size() < maxNumFriends) {
                    p.addFriend(possibleFriend);
                    numFriendsToAdd--;
                }
            }

            while (numEnemiesToAdd > 0) {
                Person possibleEnemy = pool.get(rand.nextInt(pool.size()));
                if (!p.isFriend(possibleEnemy) && !p.isEnemy(possibleEnemy) && p != possibleEnemy
                        && possibleEnemy.getEnemies().size() < maxNumEnemies) {
                    p.addEnemy(possibleEnemy);
                    numEnemiesToAdd--;
                }
            }

        }
    }

}