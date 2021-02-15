package com.patrick.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Effect extends AnimatedEntity {

    protected boolean done;
    protected int parentId;

    public boolean isDone() {
        return this.done;
    }

    public void setDone() {
        this.done = true;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Effect(Vector2 position, Animation<TextureRegion> animation, TextureRegion[][] textureRegions, int parentId, int animSpeed) {
        super(position, null);
        this.done = false;
        this.animation = animation;
        this.textureRegions = textureRegions;
        this.collider = new Rectangle(position.x, position.y, 1, 1);
        this.parentId = parentId;
        this.animSpeed = animSpeed;
        this.start();
    }

    public void update(float delta) {
        if(this.animFrame >= this.animation.getKeyFrames().length - 1) this.done = true;
        super.update(delta);
    }

    public void move(Vector2 position) {
        super.move(position);
    }
}
