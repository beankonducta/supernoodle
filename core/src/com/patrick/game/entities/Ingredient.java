package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ingredient extends StaticEntity {

    protected Vector2 offset;

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public void resetOffset() {
        this.offset.x = 0;
        this.offset.y = 0;
    }

    public Ingredient(Vector2 position, float speed, float weight, float decelSpeed, Texture texture, int id) {
        super(position, speed, weight, decelSpeed, texture);
        this.id = id;
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        this.offset = new Vector2(0, 0);
    }

    public void move(Vector2 position) {
        // not working. oh well.
        super.move(position.add(this.offset));
        this.resetOffset();
    }
}
