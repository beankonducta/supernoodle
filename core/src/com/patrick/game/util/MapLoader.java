package com.patrick.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapLoader {

    public MapLoader() {
    }

    private Pixmap flipPixmap(Pixmap src) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        Pixmap flipped = new Pixmap(width, height, src.getFormat());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                flipped.drawPixel(x, y, src.getPixel(x, height - y - 1));
            }
        }
        return flipped;
    }

    // TODO: Consider having this return player, bowls and ingredients individually or in a different list (so we arent iterating so much)
    public List<Entity> loadMap(String path) {
        Pixmap pixmap = flipPixmap(new Pixmap(Gdx.files.internal(path)));
        List<Entity> entities = new ArrayList<Entity>();
        int playerCount = 1;
        int ingredientCount = 3;
        int bowlCount = 8;
        int otherCount = 10;
        for(int i = 0; i < pixmap.getWidth(); i ++) {
            for(int j = 0; j < pixmap.getHeight(); j ++) {
                Color c = new Color(pixmap.getPixel(i, j));
                if(c.getRed() == 0 && c.getGreen() == 0) {
                    entities.add(
                            new Floor(
                                    new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j)));
                }
                if(c.getRed() == 255 && c.getBlue() == 255 && c.getGreen() < 10) {
                    if(playerCount <= 2) {
                        entities.add(
                                new Player(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED,
                                        playerCount));
                        playerCount++;
                    }
                }
                if(c.getBlue() == 255 && c.getGreen() == 255 && c.getRed() < 10) {
                    if(ingredientCount <= 7) {
                        entities.add(
                                new Ingredient(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.INGREDIENT_SPEED,
                                        Settings.INGREDIENT_WEIGHT,
                                        Settings.INGREDIENT_DECEL_SPEED,
                                        ingredientCount));
                        ingredientCount++;
                    }
                }
                if(c.getRed() == 255 && c.getGreen() == 255) {
                    if (bowlCount <= 9) {
                        entities.add(
                                new Bowl(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        bowlCount));
                        bowlCount++;
                    }
                }
            }
        }
        for(int c = 0; c < Settings.CLOUD_COUNT; c++) {
            int x = Math.RANDOM_BETWEEN(0, pixmap.getWidth() * Settings.TILE_SIZE);
            int y = Math.RANDOM_BETWEEN((int)(pixmap.getHeight() * Settings.TILE_SIZE * .6f), pixmap.getHeight() * Settings.TILE_SIZE);
            entities.add(new Cloud(new Vector2(x, y), Math.RANDOM_BETWEEN(Settings.CLOUD_MIN_SPEED, Settings.CLOUD_MAX_SPEED), Math.EITHER_OR(-5, 100)));
        }
        Collections.sort(entities);
        return entities;
    }
}