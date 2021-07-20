package com.patrick.game.util;

import java.util.concurrent.ThreadLocalRandom;

public class Math {

    public static int RANDOM_BETWEEN(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static float FLOAT_RANDOM_BETWEEN(float min, float max) {
        return ThreadLocalRandom.current().nextInt((int)min, (int)max + 1);
    }

    public static int EITHER_OR(int either, int or) {
        int random = ThreadLocalRandom.current().nextInt(either, or + 1);
        if(random - either > or - random) return or;
        return either;
    }

    public static int RANDOM_POS_NEG(int value) {
        int random = ThreadLocalRandom.current().nextInt(0,  2);
        if(random == 0) return -1*value;
        return value;
    }
}