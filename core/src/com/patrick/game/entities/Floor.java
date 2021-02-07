package com.patrick.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Floor extends StaticEntity {

    protected TextureRegion[][] textures;

    public Floor(Vector2 position) {
        super(position, new Texture(Gdx.files.internal("FLOOR.png"))); // + math.random(5) (and just have multiple textures in the folder)
        this.debugColor = Color.BLUE;
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
}
