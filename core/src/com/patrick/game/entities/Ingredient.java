package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Ingredient extends StaticEntity {

    protected Rectangle pickupCollider;
    protected boolean held;
    protected boolean wasHeld;

    public boolean isHeld() {
        return this.held;
    }

    public void setHeld(boolean held) {
        this.wasHeld = this.held;
        this.held = held;
    }

    public boolean wasHeld() {
        return this.wasHeld;
    }

    public void setWasHeld(boolean wasHeld) {
        this.wasHeld = wasHeld;
    }

    public Rectangle getPickupCollider() {
        return this.pickupCollider;
    }

    /**
     * Updates texture based on id (minus 3, because we start our ingredient id at 3)
     *
     */
    public void updateTexture() {
        this.texture = Resources.INGREDIENT(this.id - 3);
    }

    public Ingredient(Vector2 position, float speed, float weight, float decelSpeed, int id) {
        super(position, speed, weight, decelSpeed, null);
        this.id = id;
        this.collider = new Rectangle(position.x + .2f, position.y, Settings.TILE_SIZE * .6f, Settings.TILE_SIZE);
        this.pickupCollider = new Rectangle(position.x - (Settings.TILE_SIZE / 2), position.y - (Settings.TILE_SIZE / 2), Settings.TILE_SIZE * 2, Settings.TILE_SIZE * 2);
        this.floorCollider = new Rectangle(this.x() - Settings.TILE_SIZE, this.y(), Settings.TILE_SIZE * 2, Settings.TILE_SIZE);
        this.debugColor = Color.BLACK;
        this.updateTexture();
    }

    /**
     * Update the ingredient. Move colliders to match position.
     *
     * @param delta
     */
    public void update(float delta) {
        super.update(delta);
        if (this.pickupCollider != null)
            this.pickupCollider.setPosition(new Vector2(this.x() - (Settings.TILE_SIZE / 2), this.y() - (Settings.TILE_SIZE / 2)));
        if (this.collider != null)
            this.collider.setPosition(new Vector2(this.x() + .2f, this.y()));
        if (this.floorCollider != null)
            this.floorCollider.setPosition(new Vector2(this.x() - (Settings.TILE_SIZE / 2), this.y()));
    }

    /**
     * Draw the ingredient.
     *
     * @param batch
     */
    public void draw(Batch batch) {
        super.draw(batch);
    }

    /**
     * Move the ingredient by provided position.
     *
     * @param position
     */
    public void move(Vector2 position) {
        super.move(position);
    }
}
