package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AnimatedEntity extends Entity {

    protected Texture texture;
    protected TextureRegion[][] textureRegions; // might need to be 2d array
    protected Animation<TextureRegion> animation;
    protected Rectangle collider;
    protected int animFrame;

    public AnimatedEntity(Vector2 position) {
        this.position = position;
        this.speed = 0;
        this.velocity = 0;
        this.animFrame = 0;
    }

    public AnimatedEntity(Vector2 position, float speed, float velocity) {
        this.position = position;
        this.speed = speed;
        this.velocity = velocity;
        this.animFrame = 0;
    }

    public AnimatedEntity(Vector2 position, float speed, float velocity, Texture texture) {
        this.position = position;
        this.speed = speed;
        this.velocity = velocity;
        this.texture = texture;
        this.animFrame = 0;
    }

    @Override
    public void update() {
        if (this.animFrame < this.textureRegions[0].length - 1) this.animFrame++;
        else this.animFrame = 0;
    }

    @Override
    public void draw(Batch batch) {
        if (this.texture == null) return;
        batch.draw(this.animation.getKeyFrame(this.animFrame, true), this.position.x, this.position.y);
    }

    @Override
    public void move(Vector2 direction) {
        this.position = this.position.add(direction);
        this.collider.setPosition(this.position);
    }
}