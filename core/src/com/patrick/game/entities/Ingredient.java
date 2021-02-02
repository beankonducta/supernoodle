package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ingredient extends StaticEntity {
    public Ingredient(Vector2 position, float speed, float weight, float decelSpeed, Texture texture) {
        super(position, speed, weight, decelSpeed, texture);
    }
}
