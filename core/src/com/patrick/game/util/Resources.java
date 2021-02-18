package com.patrick.game.util;

import com.badlogic.gdx.Gdx;
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
    private static final TextureRegion[][] PLAYER_TEXTURE_RUN_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_RUN.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_RUN_ANIMATION = new Animation(1f, PLAYER_TEXTURE_RUN_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_RUN_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_RUN_HOLD.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_RUN_HOLD_ANIMATION = new Animation(1f, PLAYER_TEXTURE_RUN_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_JUMP_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_JUMP.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_JUMP_ANIMATION = new Animation(1f, PLAYER_TEXTURE_JUMP_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_JUMP_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_JUMP_HOLD.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_JUMP_HOLD_ANIMATION = new Animation(1f, PLAYER_TEXTURE_JUMP_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_FALL_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_FALL.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_FALL_ANIMATION = new Animation(1f, PLAYER_TEXTURE_FALL_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_FALL_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_FALL_HOLD.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_FALL_HOLD_ANIMATION = new Animation(1f, PLAYER_TEXTURE_FALL_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_STILL_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_STILL.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_STILL_ANIMATION = new Animation(1f, PLAYER_TEXTURE_STILL_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_STILL_HOLD_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_STILL_HOLD.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_STILL_HOLD_ANIMATION = new Animation(1f, PLAYER_TEXTURE_STILL_HOLD_REGION[0]);
    private static final TextureRegion[][] PLAYER_TEXTURE_DANCE_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAYER_DANCE.png")), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
    private static final Animation<TextureRegion> PLAYER_DANCE_ANIMATION = new Animation(1f, PLAYER_TEXTURE_DANCE_REGION[0]);

    public static TextureRegion[][] GET_PLAYER_TEXTURE_REGION(String name, boolean hasIngredient) {
        switch(name) {
            case "RUN":
                if(hasIngredient) return PLAYER_TEXTURE_RUN_HOLD_REGION;
                return PLAYER_TEXTURE_RUN_REGION;
            case "JUMP":
                if(hasIngredient) return PLAYER_TEXTURE_JUMP_HOLD_REGION;
                return PLAYER_TEXTURE_JUMP_REGION;
            case "FALL":
                if(hasIngredient) return PLAYER_TEXTURE_FALL_HOLD_REGION;
                return PLAYER_TEXTURE_FALL_REGION;
            case "STILL":
                if(hasIngredient)return PLAYER_TEXTURE_STILL_HOLD_REGION;
                return PLAYER_TEXTURE_STILL_REGION;
            case "DANCE":
                return PLAYER_TEXTURE_DANCE_REGION;
            default:
                return PLAYER_TEXTURE_STILL_REGION;
        }
    }

    public static Animation<TextureRegion> GET_PLAYER_ANIMATION(String name, boolean hasIngredient) {
        switch(name) {
            case "RUN":
                if(hasIngredient) return PLAYER_RUN_HOLD_ANIMATION;
                return PLAYER_RUN_ANIMATION;
            case "JUMP":
                if(hasIngredient) return PLAYER_JUMP_HOLD_ANIMATION;
                return PLAYER_JUMP_ANIMATION;
            case "FALL":
                if(hasIngredient) return PLAYER_FALL_HOLD_ANIMATION;
                return PLAYER_FALL_ANIMATION;
            case "STILL":
                if(hasIngredient)return PLAYER_STILL_HOLD_ANIMATION;
                return PLAYER_STILL_ANIMATION;
            case "DANCE":
                return PLAYER_DANCE_ANIMATION;
            default:
                return PLAYER_STILL_ANIMATION;
        }
    }

    // ingredients
    private static final TextureRegion[][] INGREDIENTS_REGION = Sprite.split(new Texture(Gdx.files.internal("INGREDIENTS.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);

    public static TextureRegion INGREDIENT(int index) {
        if(index >= INGREDIENTS_REGION[0].length) return null;
        return INGREDIENTS_REGION[0][index];
    }

    // floor
    private static final TextureRegion[][] FLOOR_REGION = Sprite.split(new Texture(Gdx.files.internal("GROUND.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);

    public static TextureRegion FLOOR(int index) {
        if(index >= FLOOR_REGION[0].length) return null;
        return FLOOR_REGION[0][index];
    }

    // bowls
    private static final TextureRegion[][] BOWLS_REGION = Sprite.split(new Texture(Gdx.files.internal("BOWLS.png")), Settings.TILE_SIZE, Settings.TILE_SIZE);

    public static TextureRegion BOWL(int index) {
        if(index >= BOWLS_REGION[0].length) return null;
        return BOWLS_REGION[0][index];
    }

    // clouds
    private static final TextureRegion[][] CLOUDS_REGION = Sprite.split(new Texture(Gdx.files.internal("CLOUDS.png")), Settings.TILE_SIZE * 4, Settings.TILE_SIZE * 2);

    public static TextureRegion CLOUD(int index) {
        if(index >= CLOUDS_REGION[0].length) return null;
        return CLOUDS_REGION[0][index];
    }

    // background
    private static final TextureRegion[][] MOUNTAIN_REGION = Sprite.split(new Texture(Gdx.files.internal("MOUNTAINS.png")), 1024, 683);

    public static TextureRegion MOUNTAIN(int index) {
        if(index >= MOUNTAIN_REGION[0].length - 1) return null;
        return MOUNTAIN_REGION[0][index];
    }

    // hud
    public static final TextureRegion[][] PLAQUES_REGION = Sprite.split(new Texture(Gdx.files.internal("PLAQUES.png")), 112, 96);

    public static TextureRegion PLAQUE(int id, int count) {
        if(id > 2) return null;
        if(count >= PLAQUES_REGION[id-1].length) return null;
        return PLAQUES_REGION[id-1][count];
    }

    public static float PLAQUE_WIDTH = PLAQUE(1, 0).getRegionWidth();
    public static float PLAQUE_HEIGHT = PLAQUE(1, 0).getRegionHeight();

    public static final TextureRegion[][] START_PLAQUES_REGION = Sprite.split(new Texture(Gdx.files.internal("START_PLAQUES.png")), 112, 36);

    public static TextureRegion START_PLAQUE(int id, boolean started) {
        if(id > 2) return null;
        return START_PLAQUES_REGION[id-1][started ? 1 : 0];
    }

    public static float START_PLAQUE_WIDTH = PLAQUE(1, 0).getRegionWidth();
    public static float START_PLAQUE_HEIGHT = PLAQUE(1, 0).getRegionHeight();

    public static final Texture LOGO = new Texture(Gdx.files.internal("LOGO.png"));

    public static final TextureRegion[][] COUNTDOWN_REGION = Sprite.split(new Texture(Gdx.files.internal("COUNTDOWN.png")), 32, 32);

    public static TextureRegion COUNTDOWN(int seconds) {
        if(seconds > 5) return COUNTDOWN_REGION[0][6];
        return COUNTDOWN_REGION[0][seconds];
    }

    // effects
    public static final TextureRegion[][] TIMER_REGION = Sprite.split(new Texture(Gdx.files.internal("TIMER.png")), 8, 8);
    public static final Animation<TextureRegion> TIMER_ANIMATION = new Animation(1f, TIMER_REGION[0]);

    public static final TextureRegion[][] GRASS_ANIM_REGION = Sprite.split(new Texture(Gdx.files.internal("GRASS_ANIM.png")), 1, 1);
    public static final Animation<TextureRegion> GRASS_ANIMATION = new Animation(1f, GRASS_ANIM_REGION[0]);

    public static final TextureRegion[][] BOWL_ANIM_REGION = Sprite.split(new Texture(Gdx.files.internal("BOWL_ANIM.png")), 1, 1);
    public static final Animation<TextureRegion> BOWL_ANIMATION = new Animation(1f, BOWL_ANIM_REGION[0]);

    public static final TextureRegion[][] RAIN_ANIM_REGION = Sprite.split(new Texture(Gdx.files.internal("RAIN_ANIM.png")), 1, 1);
    public static final Animation<TextureRegion> RAIN_ANIMATION = new Animation(1f, BOWL_ANIM_REGION[0]);

}
