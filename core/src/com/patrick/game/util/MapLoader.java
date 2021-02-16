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

    public List<Entity> loadMap(String path) {
        Pixmap pixmap = flipPixmap(new Pixmap(Gdx.files.internal(path)));
        List<Entity> entities = new ArrayList<Entity>();
        int playerCount = 1;
        int ingredientCount = 3;
        int bowlCount = 8;
        int otherCount = 10;
        for (int i = 0; i < pixmap.getWidth(); i++) {
            for (int j = 0; j < pixmap.getHeight(); j++) {
                Color c = new Color(pixmap.getPixel(i, j));
                if (c.getRed() == 0 && c.getGreen() == 0) {
                    entities.add(
                            new Floor(
                                    new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j)));
                }
                if (c.getRed() == 255 && c.getBlue() == 255 && c.getGreen() < 10) {
                    if (playerCount <= 2) {
                        entities.add(
                                new Player(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED,
                                        playerCount));
                        playerCount++;
                    }
                }
                if (c.getBlue() == 255 && c.getGreen() == 255 && c.getRed() < 10) {
                    if (ingredientCount <= 7) {
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
                if (c.getRed() == 255 && c.getGreen() == 255) {
                    if (bowlCount <= 9) {
                        entities.add(
                                new Bowl(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        bowlCount - 11));
                        bowlCount++;
                    }
                }
            }
        }
        for (int c = 0; c < Settings.CLOUD_COUNT; c++) {
            int x = Math.RANDOM_BETWEEN(0, pixmap.getWidth() * Settings.TILE_SIZE);
            int y = Math.RANDOM_BETWEEN(0, pixmap.getHeight() * Settings.TILE_SIZE);
            entities.add(new Cloud(new Vector2(x, y), Math.RANDOM_BETWEEN(Settings.CLOUD_MIN_SPEED, Settings.CLOUD_MAX_SPEED), Math.EITHER_OR(-100, 100)));
        }
        Collections.sort(entities);
        return entities;
    }

    public Map loadMapToMap(String path) {
        Map map = new Map();
        Pixmap pixmap = flipPixmap(new Pixmap(Gdx.files.internal(path)));
        int playerCount = 1;
        int ingredientCount = 3;
        int bowlCount = 8;
        int otherCount = 10;
        for (int i = 0; i < pixmap.getWidth(); i++) {
            for (int j = 0; j < pixmap.getHeight(); j++) {
                Color c = new Color(pixmap.getPixel(i, j));
                if (c.getRed() == 0 && c.getGreen() == 0) {
                    map.getFloors().add(
                            new Floor(
                                    new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j)));
                }
                if (c.getRed() == 255 && c.getBlue() == 255 && c.getGreen() < 10) {
                    if (playerCount <= 2) {
                        map.getPlayers().add(
                                new Player(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED,
                                        playerCount));
                        playerCount++;
                    }
                }
                if (c.getBlue() == 255 && c.getGreen() == 255 && c.getRed() < 10) {
                    if (ingredientCount <= 7) {
                        map.getIngredients().add(
                                new Ingredient(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.INGREDIENT_SPEED,
                                        Settings.INGREDIENT_WEIGHT,
                                        Settings.INGREDIENT_DECEL_SPEED,
                                        ingredientCount));
                        ingredientCount++;
                    }
                }
                if (c.getRed() == 255 && c.getGreen() == 255) {
                    if (bowlCount <= 9) {
                        map.getBowls().add(
                                new Bowl(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        bowlCount - 11));
                        bowlCount++;
                    }
                }
            }
        }
        for (int c = 0; c < Settings.CLOUD_COUNT; c++) {
            int x = Math.RANDOM_BETWEEN(0, pixmap.getWidth() * Settings.TILE_SIZE);
            int y = Math.RANDOM_BETWEEN(Settings.TILE_SIZE * 12, pixmap.getHeight() * Settings.TILE_SIZE);
            map.getClouds().add(new Cloud(new Vector2(x, y), Math.RANDOM_BETWEEN(Settings.CLOUD_MIN_SPEED, Settings.CLOUD_MAX_SPEED), Math.EITHER_OR(-100, 100)));
        }
        return map;
    }
}