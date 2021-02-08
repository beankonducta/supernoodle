package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Direction;

public class AnimatedEntity extends Entity {

    private final float animOffsetMax = 3f;
    protected TextureRegion[][] textureRegions; // might need to be 2d array
    protected Animation<TextureRegion> animation;
    protected int animFrame;
    protected int animOffset;
    protected boolean playAnimation;

    public void setAnimation(TextureRegion[][] textureRegions, Animation<TextureRegion> animation) {
        this.textureRegions = textureRegions;
        this.animation = animation;
    }

    public AnimatedEntity(Vector2 position, Texture texture) {
        super(position, texture);
        this.animFrame = 0;
        this.animOffset = 0;
        this.playAnimation = false;
    }

    public AnimatedEntity(Vector2 position, float speed, float weight, float decelSpeed, Texture texture) {
        super(position, speed, weight, decelSpeed, texture);
        this.animFrame = 0;
        this.animOffset = 0;
    }

    public void update(float delta) {
        super.update(delta);
        // TODO: Incorporate delta here
        if (this.playAnimation) {
            animOffset += 1;
            if(animOffset >= animOffsetMax) {
                animOffset = 0;
                if (this.animFrame < this.textureRegions[0].length - 1) this.animFrame++;
                else this.animFrame = 0;
            }
        }
    }

    public void draw(Batch batch, ShapeRenderer renderer) {
        super.draw(batch, renderer);
        if (this.textureRegions == null) return;
        TextureRegion t = this.animation.getKeyFrame(this.animFrame, true);
        if(this.dir == Direction.LEFT && !t.isFlipX()) t.flip(true, false);
        if(this.dir == Direction.RIGHT && t.isFlipX()) t.flip(true, false);
        batch.draw(t, this.position.x, this.position.y);
    }

    public void move(Vector2 position) {
        if(position.x > 0) this.dir = Direction.RIGHT;
        if(position.x < 0) this.dir = Direction.LEFT;
        if (Math.abs(velocity) > 0 || Math.abs(heightGain) > 0)
            this.playAnimation = true;
        else
            this.playAnimation = false;
        super.move(position);
    }

    public void start() {
        this.playAnimation = true;
    }

    public void stop() {
        this.playAnimation = false;
    }
}