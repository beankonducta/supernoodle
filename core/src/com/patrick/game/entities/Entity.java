package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {

    protected Vector2 position;
    protected float speed;
    protected float velocity;
    protected float weight;
    protected float decelSpeed;
    protected Texture texture;
    protected Rectangle collider;
    protected boolean moving;

    public Entity(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;
        this.speed = 0;
        this.velocity = 0;
        this.weight = 0;
        this.decelSpeed = 0;
    }

    public Entity(Vector2 position, float speed, float velocity, float weight, float decelSpeed, Texture texture) {
        this.position = position;
        this.speed = speed;
        this.velocity = velocity;
        this.decelSpeed = decelSpeed;
        this.weight = weight;
        this.texture = texture;
    }

    public void update() {
        this.position.add(new Vector2(0, -this.weight));
        if(!this.moving && this.velocity > 0) this.velocity -= this.decelSpeed;
    }

    public void draw(Batch batch) {

    }

    public void move(Vector2 direction) {
        this.position = this.position.add(direction);
        this.collider.setPosition(this.position);
    }
}