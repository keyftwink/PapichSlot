package com.papich.game.slot.math;

import java.util.Random;

public class Rnd {

    private static final Random random = new Random();

    public static float nextFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static int nextInt(int min, int max) {
        return random.nextInt( (max - min) + 1) + min;
    }
}
