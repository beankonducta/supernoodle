package com.patrick.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    protected Vector2 position;

    protected float speed;
    protected float velocity;

    public abstract void update();
    public abstract void draw(Batch batch);
    public abstract void move(Vector2 direction);

}
