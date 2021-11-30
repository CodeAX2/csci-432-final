package edu.montana;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        List<Person> guests = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Person cur = new Person();
            guests.add(cur);
        }
        Person.createRandomRelations(5, 5, guests);

        new WeddingSeating(10, guests, new SimpleCountScorer());
    }
}
