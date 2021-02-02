package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class AnimatedEntity extends Entity {

    protected TextureRegion[][] textureRegions; // might need to be 2d array
    protected Animation<TextureRegion> animation;
    protected int animFrame;

    public AnimatedEntity(Vector2 position, Texture texture) {
        super(position, texture);
        this.animFrame = 0;
    }

    public AnimatedEntity(Vector2 position, float speed, float weight, float decelSpeed, Texture texture) {
        super(position, speed, weight, decelSpeed, texture);
        this.animFrame = 0;
    }

    public void update() {
        super.update();
        if (this.animFrame < this.textureRegions[0].length - 1) this.animFrame++;
        else this.animFrame = 0;
    }

    public void draw(Batch batch, ShapeRenderer renderer) {
        super.draw(batch, renderer);
        if (this.texture == null) return;
        batch.draw(this.animation.getKeyFrame(this.animFrame, true), this.position.x, this.position.y);
    }

    public void move(Vector2 position) {
        super.move(position);
    }
}