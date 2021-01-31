package com.patrick.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {

    private float x;
    private float y;

    private float speed;
    private float velocity;

    public abstract void update();
    public abstract void draw(Batch batch);

}
