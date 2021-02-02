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
    protected float heightGain;
    protected Texture texture;
    protected Rectangle collider;

    protected int id;

    public Vector2 getPosition() {
        return position;
    }

    public void setHeightGain(float heightGain) {
        this.heightGain = heightGain;
    }

    public float getHeightGain() {
        return heightGain;
    }

    public int getId() {
        return id;
    }

    public float getSpeed() {
        return speed;
    }

    public float getVelocity() {
        return velocity;
    }

    public float getWeight() {
        return weight;
    }

    public float getDecelSpeed() {
        return decelSpeed;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public Entity(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;

        this.speed = 0;
        this.velocity = 0;
        this.weight = 0;
        this.decelSpeed = 0;
        this.heightGain = 0;
        this.id = -1;
    }

    public Entity(Vector2 position, float speed, float weight, float decelSpeed, Texture texture) {
        this.position = position;
        this.speed = speed;
        this.decelSpeed = decelSpeed;
        this.weight = weight;
        this.texture = texture;

        this.velocity = 0;
        this.heightGain = 0;
        this.id = -1;
    }

    public Entity(Vector2 position, float speed, float weight, float decelSpeed, Texture texture, int id) {
        this.position = position;
        this.speed = speed;
        this.decelSpeed = decelSpeed;
        this.weight = weight;
        this.texture = texture;
        this.id = id;

        this.velocity = 0;
        this.heightGain = 0;
    }

    public void update() {
        this.position.add(new Vector2(this.velocity, -this.weight + this.heightGain));
        if (this.velocity > 0) this.velocity -= this.decelSpeed;
        if (this.velocity < 0) this.velocity += this.decelSpeed;
        if (this.heightGain > 0) this.heightGain -= this.decelSpeed;
        if (this.heightGain < 0) this.heightGain += this.decelSpeed;
    }

    public void draw(Batch batch) {

    }

    public void move(Vector2 direction) {
        if (this.heightGain == 0) // this line disallows player to move horizontally while in the air
            this.velocity = direction.x;
        if (this.collider != null)
            this.collider.setPosition(this.position);
    }
}