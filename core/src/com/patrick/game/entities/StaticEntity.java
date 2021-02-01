package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity extends Entity {

    public StaticEntity(Vector2 position, Texture texture) {
        super(position, texture);
    }

    public StaticEntity(Vector2 position, float speed, float velocity, float weight, float decelSpeed, Texture texture) {
        super(position, speed, velocity, weight, decelSpeed, texture);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if (this.texture == null) return;
        batch.draw(texture, this.position.x, this.position.y);
    }

    @Override
    public void move(Vector2 direction) {
        super.move(direction);
    }
}