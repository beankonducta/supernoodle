package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Ingredient extends StaticEntity {

    protected Rectangle pickupCollider;
    protected boolean held;

    public boolean isHeld() {
        return this.held;
    }

    public void setHeld(boolean held) {
        this.held = held;
    }

    public Rectangle getPickupCollider() {
        return this.pickupCollider;
    }

    public void updateTexture() {
        this.texture = Resources.INGREDIENT(this.id - 3); // - 3 because we start ingredient id at 3.
    }

    public Ingredient(Vector2 position, float speed, float weight, float decelSpeed, int id) {
        super(position, speed, weight, decelSpeed, null);
        this.id = id;
        this.collider = new Rectangle(position.x + .2f, position.y, Settings.TILE_SIZE * .6f, Settings.TILE_SIZE);
        this.pickupCollider = new Rectangle(position.x - (Settings.TILE_SIZE / 2), position.y - (Settings.TILE_SIZE / 2), Settings.TILE_SIZE*2, Settings.TILE_SIZE*2);
        this.debugColor = Color.BLACK;
        this.updateTexture();
    }

    public void update(float delta) {
        super.update(delta);
        if(this.pickupCollider != null)
            this.pickupCollider.setPosition(new Vector2(this.position.x - (Settings.TILE_SIZE / 2), this.position.y - (Settings.TILE_SIZE / 2)));
        if(this.collider != null)
            this.collider.setPosition(new Vector2(this.position.x + .2f, this.position.y));
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void move(Vector2 position) {
        super.move(position);
    }
}
