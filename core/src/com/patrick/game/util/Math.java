package com.patrick.game.util;

import java.util.concurrent.ThreadLocalRandom;

public class Math {

    public static int RANDOM_BETWEEN(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int EITHER_OR(int either, int or) {
        int random = ThreadLocalRandom.current().nextInt(either, or + 1);
        if(random - either > or - random) return or;
        return either;
    }
}
