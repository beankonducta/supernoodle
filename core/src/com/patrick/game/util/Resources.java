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
    // NOTE: player should be bigger I think.
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
    // NOTE: this should have its own size, not use tilesize
    private static final TextureRegion[][] CLOUDS_REGION = Sprite.split(new Texture(Gdx.files.internal("CLOUDS.png")), Settings.TILE_SIZE * 4, Settings.TILE_SIZE * 2);

    public static TextureRegion CLOUD(int index) {
        if(index >= CLOUDS_REGION[0].length) return null;
        return CLOUDS_REGION[0][index];
    }

    // hud
    // NOTE: We still need to figure out these textures (hint: check piskelapp, I was smart and saved these from the og game)

}
