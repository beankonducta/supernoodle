package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity extends Entity {


    public StaticEntity(Vector2 position, TextureRegion texture) {
        super(position, texture);
    }

    public StaticEntity(Vector2 position, float speed, float weight, float decelSpeed, TextureRegion texture) {
        super(position, speed, weight, decelSpeed, texture);
    }

    public void update(float delta) {
        super.update(delta);
    }

    public void draw(Batch batch) {
        super.draw(batch);
        if (this.texture == null) return;
        batch.draw(this.texture, this.x(), this.y());
    }

    public void move(Vector2 position) {
        super.move(position);
    }
}