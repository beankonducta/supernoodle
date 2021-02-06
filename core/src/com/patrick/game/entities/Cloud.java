package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Cloud extends StaticEntity {
    // should make a generic constructor that randomizes texture and speed
    public Cloud(Vector2 position, float speed, Texture texture) {
        super(position, speed, 0, 0, texture);
    }
}
