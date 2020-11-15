package com.kolis.test_catalog_app.util;

public class PasswordGenerator {
    private static String[] adjective = {"sweet", "black", "huge", "my", "litte", "bright"};
    private static String[] noun = {"Darling", "Lord", "Rainbow", "King", "Cowboy", "Tower"};

    public static String getRandomLogin() {

        return "" + adjective[(int) ((adjective.length - 1) * Math.random())] +
                noun[(int) ((noun.length - 1) * Math.random())] +
                (int) (Math.random() * 10000);
    }

    public static String getRandomPassword() {
        return (int) (Math.random() * 100000) + "";
    }


}
