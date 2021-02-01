package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Cloud extends StaticEntity {
    public Cloud(Vector2 position, float speed, float velocity, Texture texture) {
        super(position, speed, velocity, 0, 0, texture);
    }
}
