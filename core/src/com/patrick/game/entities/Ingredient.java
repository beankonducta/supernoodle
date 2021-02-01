package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Ingredient extends StaticEntity {
    public Ingredient(Vector2 position, float speed, float velocity, float weight, float decelSpeed, Texture texture) {
        super(position, speed, velocity, weight, decelSpeed, texture);
    }
}
