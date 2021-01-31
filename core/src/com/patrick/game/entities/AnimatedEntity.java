package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class AnimatedEntity extends Entity {

    private Texture texture;
    private TextureRegion[] textureRegions; // might need to be 2d array
    private Animation<TextureRegion> animation;
    private Rectangle collider;

    public AnimatedEntity() {
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Batch batch) {
    }

    public void move() {
    }
}