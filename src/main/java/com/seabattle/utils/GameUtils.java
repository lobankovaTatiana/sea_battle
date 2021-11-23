package com.seabattle.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameUtils {
    public static final int SIZE = 10;
    public final static String PC_PREFIX = "PC";

    private GameUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<Integer, Integer> ships() {
        Map<Integer, Integer> ships = new HashMap<>();
        ships.put(1, 4);
        ships.put(2, 3);
        ships.put(3, 2);
        ships.put(4, 1);
        return Collections.unmodifiableMap(ships);
    }

    public static int getRandomIntegerBetweenRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
