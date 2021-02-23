package com.patrick.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity extends Entity {

    /**
     * Static Entities essentially just have one key frame (single texture / graphic) which is unchanging.
     *
     */

    public StaticEntity(Vector2 position, TextureRegion texture) {
        super(position, texture);
    }

    public StaticEntity(Vector2 position, float speed, float weight, float decelSpeed, TextureRegion texture) {
        super(position, speed, weight, decelSpeed, texture);
    }

    /**
     * Update the static entity.
     *
     * @param delta
     */
    public void update(float delta) {
        super.update(delta);
    }

    /**
     * Draw the entities texture.
     *
     * @param batch
     */
    public void draw(Batch batch) {
        super.draw(batch);
        if (this.texture == null) return;
        batch.draw(this.texture, this.x(), this.y());
    }

    /**
     * Move the entity by provided position.
     *
     * @param position
     */
    public void move(Vector2 position) {
        super.move(position);
    }
}