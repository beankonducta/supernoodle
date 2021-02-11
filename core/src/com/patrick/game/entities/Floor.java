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

    public void updateTexture() {
        this.texture = Resources.FLOOR(Math.RANDOM_BETWEEN(0, 2)); // 2 should be actual length of the floor texture region
    }

    public Floor(Vector2 position) {
        super(position, null);
        this.debugColor = Color.BLUE;
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.updateTexture();
    }
}
