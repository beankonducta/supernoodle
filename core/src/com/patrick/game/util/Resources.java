package com.patrick.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resources {

    /*
    Textures are sprite sheets that are SINGLE ROW. So animations must be loaded from TextureRegion[0].

    Individual textures can be retrieved from regions via: TextureRegion[0][x].getTexture();
     */

    // player
    // one
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_RUN_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_RUN_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_RUN_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_RUN_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_RUN_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_RUN_HOLD_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_RUN_HOLD_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_RUN_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_JUMP_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_JUMP_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_JUMP_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_JUMP_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_JUMP_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_JUMP_HOLD_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_JUMP_HOLD_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_JUMP_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_FALL_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_FALL_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_FALL_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_FALL_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_FALL_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_FALL_HOLD_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_FALL_HOLD_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_FALL_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_STILL_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_STILL_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_STILL_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_STILL_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_STILL_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_STILL_HOLD_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_STILL_HOLD_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_STILL_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_ONE_TEXTURE_DANCE_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_DANCE_1.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_ONE_DANCE_ANIMATION = new Animation(1f, PLAYER_ONE_TEXTURE_DANCE_REGION[0]);

    // two
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_RUN_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_RUN_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_RUN_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_RUN_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_RUN_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_RUN_HOLD_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_RUN_HOLD_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_RUN_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_JUMP_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_JUMP_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_JUMP_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_JUMP_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_JUMP_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_JUMP_HOLD_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_JUMP_HOLD_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_JUMP_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_FALL_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_FALL_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_FALL_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_FALL_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_FALL_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_FALL_HOLD_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_FALL_HOLD_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_FALL_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_STILL_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_STILL_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_STILL_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_STILL_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_STILL_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_STILL_HOLD_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_STILL_HOLD_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_STILL_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TWO_TEXTURE_DANCE_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_DANCE_2.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_TWO_DANCE_ANIMATION = new Animation(1f, PLAYER_TWO_TEXTURE_DANCE_REGION[0]);

    public static TextureRegion[][] GET_PLAYER_TEXTURE_REGION(String name, boolean hasIngredient, int id) {
        switch (name) {
            case "RUN":
                if (hasIngredient)
                    return id == 1 ? PLAYER_ONE_TEXTURE_RUN_HOLD_REGION : PLAYER_TWO_TEXTURE_RUN_HOLD_REGION;
                return id == 1 ? PLAYER_ONE_TEXTURE_RUN_REGION : PLAYER_TWO_TEXTURE_RUN_REGION;
            case "JUMP":
                if (hasIngredient)
                    return id == 1 ? PLAYER_ONE_TEXTURE_JUMP_HOLD_REGION : PLAYER_TWO_TEXTURE_JUMP_HOLD_REGION;
                return id == 1 ? PLAYER_ONE_TEXTURE_JUMP_REGION : PLAYER_TWO_TEXTURE_JUMP_REGION;
            case "FALL":
                if (hasIngredient)
                    return id == 1 ? PLAYER_ONE_TEXTURE_FALL_HOLD_REGION : PLAYER_TWO_TEXTURE_FALL_HOLD_REGION;
                return id == 1 ? PLAYER_ONE_TEXTURE_FALL_REGION : PLAYER_TWO_TEXTURE_FALL_REGION;
            case "STILL":
                if (hasIngredient)
                    return id == 1 ? PLAYER_ONE_TEXTURE_STILL_HOLD_REGION : PLAYER_TWO_TEXTURE_STILL_HOLD_REGION;
                return id == 1 ? PLAYER_ONE_TEXTURE_STILL_REGION : PLAYER_TWO_TEXTURE_STILL_REGION;
            case "DANCE":
                return id == 1 ? PLAYER_ONE_TEXTURE_DANCE_REGION : PLAYER_TWO_TEXTURE_DANCE_REGION;
            default:
                return id == 1 ? PLAYER_ONE_TEXTURE_STILL_REGION : PLAYER_TWO_TEXTURE_STILL_REGION;
        }
    }

    public static Animation<TextureRegion> GET_PLAYER_ANIMATION(String name, boolean hasIngredient, int id) {
        switch (name) {
            case "RUN":
                if (hasIngredient) return id == 1 ? PLAYER_ONE_RUN_HOLD_ANIMATION : PLAYER_TWO_RUN_HOLD_ANIMATION;
                return id == 1 ? PLAYER_ONE_RUN_ANIMATION : PLAYER_TWO_RUN_ANIMATION;
            case "JUMP":
                if (hasIngredient) return id == 1 ? PLAYER_ONE_JUMP_HOLD_ANIMATION : PLAYER_TWO_JUMP_HOLD_ANIMATION;
                return id == 1 ? PLAYER_ONE_JUMP_ANIMATION : PLAYER_TWO_JUMP_ANIMATION;
            case "FALL":
                if (hasIngredient) return id == 1 ? PLAYER_ONE_FALL_HOLD_ANIMATION : PLAYER_TWO_FALL_HOLD_ANIMATION;
                return id == 1 ? PLAYER_ONE_FALL_ANIMATION : PLAYER_TWO_FALL_ANIMATION;
            case "STILL":
                if (hasIngredient) return id == 1 ? PLAYER_ONE_STILL_HOLD_ANIMATION : PLAYER_TWO_STILL_HOLD_ANIMATION;
                return id == 1 ? PLAYER_ONE_STILL_ANIMATION : PLAYER_TWO_STILL_ANIMATION;
            case "DANCE":
                return id == 1 ? PLAYER_ONE_DANCE_ANIMATION : PLAYER_TWO_DANCE_ANIMATION;
            default:
                return id == 1 ? PLAYER_ONE_STILL_ANIMATION : PLAYER_TWO_STILL_ANIMATION;
        }
    }

    // ingredients
    private static final TextureRegion[][] INGREDIENTS_REGION = Sprite.split(new Texture(Gdx.files.internal("INGREDIENTS.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);

    public static TextureRegion INGREDIENT(int index) {
        if (index >= INGREDIENTS_REGION[0].length) return null;
        return INGREDIENTS_REGION[0][index];
    }

    // floor
    private static final TextureRegion[][] FLOOR_REGION = Sprite.split(new Texture(Gdx.files.internal("GROUND.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);

    public static TextureRegion FLOOR(int index) {
        if (index >= FLOOR_REGION[0].length) return null;
        return FLOOR_REGION[0][index];
    }

    public static final int FLOOR_TEXTURES_LENGTH = FLOOR_REGION[0].length - 1;

    // bowls
    private static final TextureRegion[][] BOWLS_REGION = Sprite.split(new Texture(Gdx.files.internal("BOWLS.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);

    public static TextureRegion BOWL(int index) {
        if (index >= BOWLS_REGION[0].length) return null;
        return BOWLS_REGION[0][index];
    }

    // clouds
    private static final TextureRegion[][] CLOUDS_REGION = Sprite.split(new Texture(Gdx.files.internal("CLOUDS.png")), Settings.TILE_SIZE * 4, Settings.TILE_SIZE * 2);

    public static TextureRegion CLOUD(int index) {
        if (index >= CLOUDS_REGION[0].length) return null;
        return CLOUDS_REGION[0][index];
    }

    public static final int CLOUD_TEXTURES_LENGTH = CLOUDS_REGION[0].length - 1;

    // background
    public static final Texture MOUNTAINS = new Texture(Gdx.files.internal("MOUNTAINS.png"));

    // hud
    public static final TextureRegion[][] PLAQUES_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAQUES.png")), 112, 96);

    public static TextureRegion PLAQUE(int id, int count) {
        if (id > 2) return null;
        if (count >= PLAQUES_REGION[id - 1].length) return null;
        return PLAQUES_REGION[id - 1][count];
    }

    public static float PLAQUE_WIDTH = PLAQUE(1, 0).getRegionWidth();
    public static float PLAQUE_HEIGHT = PLAQUE(1, 0).getRegionHeight();

    public static final TextureRegion[][] START_PLAQUES_REGION = Sprite.split(new Texture(Gdx.files.internal("START_PLAQUES.png")), 112, 36);

    public static TextureRegion START_PLAQUE(int id, boolean started) {
        if (id > 2) return null;
        return START_PLAQUES_REGION[id - 1][started ? 1 : 0];
    }

    public static float START_PLAQUE_WIDTH = PLAQUE(1, 0).getRegionWidth();
    public static float START_PLAQUE_HEIGHT = PLAQUE(1, 0).getRegionHeight();

    public static final Texture LOGO = new Texture(Gdx.files.internal("LOGO.png"));

    public static final TextureRegion[][] COUNTDOWN_REGION = Sprite.split(new Texture(Gdx.files.internal("COUNTDOWN.png")), 32, 32);

    public static TextureRegion COUNTDOWN(int seconds) {
        if (seconds > 5) return COUNTDOWN_REGION[0][6];
        return COUNTDOWN_REGION[0][seconds];
    }

    // effects
    public static final TextureRegion[][] TIMER_REGION = Sprite.split(new Texture(Gdx.files.internal("METER.png")), 8, 16);
    public static final Animation<TextureRegion> TIMER_ANIMATION = new Animation(1f, TIMER_REGION[0]);

    public static final TextureRegion[][] GRASS_ANIM_REGION = Sprite.split(new Texture(Gdx.files.internal("GRASS_ANIM.png")), 1, 1);
    public static final Animation<TextureRegion> GRASS_ANIMATION = new Animation(1f, GRASS_ANIM_REGION[0]);

    public static final TextureRegion[][] BOWL_ANIM_REGION = Sprite.split(new Texture(Gdx.files.internal("BOWL_ANIM.png")), 1, 1);
    public static final Animation<TextureRegion> BOWL_ANIMATION = new Animation(1f, BOWL_ANIM_REGION[0]);

    public static final TextureRegion[][] RAIN_ANIM_REGION = Sprite.split(new Texture(Gdx.files.internal("RAIN_ANIM.png")), 1, 1);
    public static final Animation<TextureRegion> RAIN_ANIMATION = new Animation(1f, BOWL_ANIM_REGION[0]);

    // sounds
    public static final Music[] MUSIC = new Music[]{
            Gdx.audio.newMusic(Gdx.files.internal("music/DRUMS.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("music/INTRO_1.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("music/INTRO_2.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("music/MAIN_1.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("music/MAIN_2.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("music/MAIN_3_HIGH.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("music/MAIN_4_MELODY.wav"))
    };

    private static final Sound WALK_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/WALK.wav"));
    private static final Sound WALK_SLOW_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/WALK_SLOW.wav"));
    private static final Sound JUMP_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/JUMP.wav"));
    private static final Sound PICKUP_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/PICKUP.wav"));
    private static final Sound BOWL_ADD_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/BOWL_ADD.wav"));
    private static final Sound BOWL_REMOVE_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/BOWL_REMOVE.wav"));
    private static final Sound WIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/WIN.wav"));
    private static final Sound COIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/COIN.wav"));

    public static Sound SOUND(String name) {
        switch(name) {
            case "walk": return WALK_SOUND;
            case "walk slow": return WALK_SLOW_SOUND;
            case "jump": return JUMP_SOUND;
            case "pickup": return PICKUP_SOUND;
            case "bowl add": return BOWL_ADD_SOUND;
            case "bowl remove": return BOWL_REMOVE_SOUND;
            case "win": return WIN_SOUND;
            case "coin": return COIN_SOUND;
        }
        return null;
    }

}