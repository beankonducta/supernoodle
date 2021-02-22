package com.patrick.game.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

public class Settings {

    public static final Color BLUE = new Color(0, .6f, .9f, 1);
    public static final Color GREEN = new Color(0, .6f, .5f, 1);

    public static final int[] PLAYER_ONE_KEYS = new int[]{Input.Keys.D, Input.Keys.A, Input.Keys.W, Input.Keys.S, Input.Keys.CONTROL_LEFT};
    public static final int[] PLAYER_TWO_KEYS = new int[]{Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.CONTROL_RIGHT};

    public static final float PLAYER_JUMP_HEIGHT = 700f;
    public static final float PLAYER_FALL_MOD = 120f;
    public static final float PLAYER_SPEED = 190f;
    public static final float PLAYER_DECEL_SPEED = 550f;
    public static final float PLAYER_WEIGHT = 260f;

    public static final float INGREDIENT_SPEED = 90f;
    public static final float INGREDIENT_DECEL_SPEED = 250f;
    public static final float INGREDIENT_WEIGHT = 340f;

    public static boolean DEBUG_COLLISION = false;
    public static boolean DEBUG_ENTITIES = false;
    public static boolean SHOW_FPS = true;

    public static final int TILE_SIZE = 16;
    public static final int VIEWPORT_WIDTH = TILE_SIZE * 64;
    public static final int VIEWPORT_HEIGHT = TILE_SIZE * 36;

    public static final int CLOUD_COUNT = 35;
    public static final int CLOUD_MIN_SPEED = -20;
    public static final int CLOUD_MAX_SPEED = 20;

    public static final float ACTION_TIME = 5f;

    public static final float DANCE_TIME = 5f;

    public static final float MAX_PARTICLE_VELOCITY = 100f;
    public static final float MAX_PARTICLE_HEIGHT_GAIN = 200f;
    public static final float DEFAULT_PARTICLE_WEIGHT = 50f;
    public static final int MAX_PARTICLE_TIME_TO_LIVE = 40;

}
