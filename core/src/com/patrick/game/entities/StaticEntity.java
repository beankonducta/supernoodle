package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity extends Entity {

    public StaticEntity(Vector2 position, Texture texture) {
        super(position, texture);
    }

    public StaticEntity(Vector2 position, float speed, float weight, float decelSpeed, Texture texture) {
        super(position, speed, weight, decelSpeed, texture);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Batch batch, ShapeRenderer renderer) {
        super.draw(batch, renderer);
        if (this.texture == null) return;
        batch.draw(texture, this.position.x, this.position.y);
    }

    @Override
    public void move(Vector2 direction) {
        super.move(direction);
    }
}