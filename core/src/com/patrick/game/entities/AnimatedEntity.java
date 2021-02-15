package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Direction;

public class AnimatedEntity extends Entity {

    private final float animOffsetMax = 2f;
    protected TextureRegion[][] textureRegions; // might need to be 2d array
    protected Animation<TextureRegion> animation;
    protected int animFrame;
    protected int animSpeed;
    protected float animOffset;
    protected boolean playAnimation;
    protected boolean forcePlayAnimation;

    public void setForcePlayAnimation(boolean forcePlayAnimation) {
        this.forcePlayAnimation = forcePlayAnimation;
    }

    public boolean getForcePlayAnimation() {
        return this.forcePlayAnimation;
    }

    public void setAnimation(TextureRegion[][] textureRegions, Animation<TextureRegion> animation) {
        this.textureRegions = textureRegions;
        this.animation = animation;
    }

    public AnimatedEntity(Vector2 position, TextureRegion texture) {
        super(position, texture);
        this.animFrame = 0;
        this.animOffset = 0;
        this.playAnimation = false;
    }

    public AnimatedEntity(Vector2 position, float speed, float weight, float decelSpeed, TextureRegion texture) {
        super(position, speed, weight, decelSpeed, texture);
        this.animFrame = 0;
        this.animOffset = 0;
    }

    public void update(float delta) {
        super.update(delta);
        if (this.playAnimation || this.forcePlayAnimation) {
            this.animOffset += this.animSpeed * delta;
            if (this.animOffset >= this.animOffsetMax) {
                this.animOffset = 0;
                if (this.animFrame < this.textureRegions[0].length - 1) this.animFrame++;
                else this.animFrame = 0;
            }
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
        if (this.animation == null) return;
        TextureRegion t = this.animation.getKeyFrame(this.animFrame, true);
        if (validDir(Direction.LEFT) && !t.isFlipX()) t.flip(true, false);
        if (validDir(Direction.RIGHT) && t.isFlipX()) t.flip(true, false);
        batch.draw(t, this.x(), this.y());
    }

    private boolean validDir(Direction dir) {
        return this.dir == dir || (this.dir == Direction.UP && this.lastDir == dir);
    }

    public void move(Vector2 position) {
        if (Math.abs(this.velocity) > 0 || Math.abs(this.heightGain) > 0)
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