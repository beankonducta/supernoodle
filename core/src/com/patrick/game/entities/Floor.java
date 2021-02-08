package com.patrick.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Math;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Floor extends StaticEntity {

    public Texture getTexture() {
        this.texture = Resources.FLOOR(Math.RANDOM_BETWEEN(0, 3)); // 3 should be actual length of the floor texture region
        return this.texture;
    }

    public Floor(Vector2 position) {
        super(position, null); // + math.random(5) (and just have multiple textures in the folder)
        this.debugColor = Color.BLUE;
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
    }
}
