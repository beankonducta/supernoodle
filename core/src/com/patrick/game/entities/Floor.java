package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Floor extends StaticEntity {

    public Floor(Vector2 position, Texture texture) {
        super(position, texture);
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
}
