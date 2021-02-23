package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Math;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Floor extends StaticEntity {

    /**
     * Update the texture to a random texture in the sheet.
     *
     */
    public void updateTexture() {
        this.texture = Resources.FLOOR(Math.RANDOM_BETWEEN(0, Resources.FLOOR_TEXTURES_LENGTH));
    }

    public Floor(Vector2 position) {
        super(position, null);
        this.debugColor = Color.BLUE;
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.updateTexture();
    }
}
