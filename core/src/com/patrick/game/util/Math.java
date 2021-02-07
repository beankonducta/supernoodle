package com.patrick.game.util;

import java.util.concurrent.ThreadLocalRandom;

public class Math {

    public static int RANDOM_BETWEEN(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
