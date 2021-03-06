package com.patrick.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Math;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Cloud extends StaticEntity {

    /**
     * Sets our texture to a random texture from the sheet.
     *
     */
    public void updateTexture() {
        this.texture = Resources.CLOUD(Math.RANDOM_BETWEEN(0, Resources.CLOUD_TEXTURES_LENGTH));
    }

    public Cloud(Vector2 position, float speed, int id) {
        super(position, speed, 0, 0, null);
        this.updateTexture();
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE * 2, Settings.TILE_SIZE);
        this.id = id;
    }

    /**
     * Draws the texture.
     *
     * @param batch
     */
    public void draw(Batch batch) {
        super.draw(batch);
        if (this.texture == null) return;
        batch.draw(this.texture, this.position.x, this.position.y);
    }
}
