package com.patrick.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Floor extends StaticEntity {

    public Floor(Vector2 position) {
        super(position, new Texture(Gdx.files.internal("BOWL.png")));
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
}
