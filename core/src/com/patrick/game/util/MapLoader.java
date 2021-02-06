package com.patrick.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {

    public MapLoader() {
    }

    public List<Entity> loadMap(String path) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(path));
        List<Entity> entities = new ArrayList<Entity>();
        int playerCount = 1;
        int ingredientCount = 3;
        for(int i = 0; i < pixmap.getWidth(); i ++) {
            for(int j = 0; j < pixmap.getHeight(); j ++) {
                Color c = new Color(pixmap.getPixel(i, j));
                if(c.getRed() == 0 && c.getGreen() == 0) {
                    entities.add(
                            new Floor(
                                    new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j)));
                }
                if(c.getRed() == 255 && c.getBlue() == 255) {
                    if(playerCount <= 2) {
                        entities.add(
                                new Player(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED,
                                        new Texture(Gdx.files.internal("PLAYER.png")),
                                        Settings.TILE_SIZE, 0.99f, playerCount));
                        playerCount++;
                    }
                }
                if(c.getRed() == 255 && c.getGreen() == 255) {
                    if(ingredientCount <= 7) {
                        entities.add(
                                new Ingredient(
                                        new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                        Settings.INGREDIENT_SPEED,
                                        Settings.INGREDIENT_WEIGHT,
                                        Settings.INGREDIENT_DECEL_SPEED,
                                        new Texture(Gdx.files.internal("INGREDIENT.png")),
                                        ingredientCount));
                        ingredientCount++;
                    }
                }
                if(c.getBlue() == 255 && c.getGreen() == 255) {
                    entities.add(
                            new Bowl(
                                    new Vector2(Settings.TILE_SIZE * i, Settings.TILE_SIZE * j),
                                    new Texture(Gdx.files.internal("BOWL.png"))));
                }
            }
        }
        return entities;
    }
}