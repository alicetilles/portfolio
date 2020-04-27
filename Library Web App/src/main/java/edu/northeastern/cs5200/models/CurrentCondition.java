package edu.northeastern.cs5200.models;

import java.util.Random;

public enum CurrentCondition {


    NEW,
    GOOD,
    ACCEPTABLE,
    WORN;


    public static CurrentCondition getRandomCondition() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }





}
