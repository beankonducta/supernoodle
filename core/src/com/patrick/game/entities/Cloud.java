package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Math;
import com.patrick.game.util.Resources;

public class Cloud extends StaticEntity {

    public Texture getTexture() {
        this.texture = Resources.CLOUD(Math.RANDOM_BETWEEN(0, 5)); // 5 should actually be the length of the texture region
        return this.texture;
    }
    // should make a generic constructor that randomizes texture and speed
    public Cloud(Vector2 position, float speed) {
        super(position, speed, 0, 0, null);
    }
}
