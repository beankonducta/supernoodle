package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Math;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Cloud extends StaticEntity {

    public void updateTexture() {
        this.texture = Resources.CLOUD(Math.RANDOM_BETWEEN(0, 4)); // 5 should actually be the length of the texture region
    }

    public Cloud(Vector2 position, float speed, int id) {
        super(position, speed, 0, 0, null);
        this.updateTexture();
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE * 2, Settings.TILE_SIZE);
        this.id = id;
    }

    @Override
    public void draw(Batch batch, ShapeRenderer renderer) {
        super.draw(batch, renderer);
        if (this.texture == null) return;
        // not changing alpha for some reason
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, .7f);
        batch.draw(this.texture, this.position.x, this.position.y);
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1);
    }
}
