package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StaticEntity extends Entity {

    protected Texture texture;
    protected Rectangle collider;

    public StaticEntity(Vector2 position) {
        this.position = position;
        this.speed = 0;
        this.velocity = 0;
    }

    public StaticEntity(Vector2 position, Texture texture) {
        this.position = position;
        this.speed = 0;
        this.velocity = 0;
    }

    public StaticEntity(Vector2 position, float speed, float velocity, Texture texture) {
        this.position = position;
        this.speed = speed;
        this.velocity = velocity;
        this.texture = texture;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Batch batch) {
        if (this.texture == null) return;
        batch.draw(texture, this.position.x, this.position.y);
    }

    @Override
    public void move(Vector2 direction) {
        this.position = this.position.add(direction);
        this.collider.setPosition(this.position);
    }
}