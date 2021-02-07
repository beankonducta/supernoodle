package com.patrick.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall extends StaticEntity {

    public Wall(Vector2 position) {
        super(position, new Texture(Gdx.files.internal("WALL.png")));
        this.debugColor = Color.PURPLE;
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
}
