package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Settings;

public class Ingredient extends StaticEntity {

    protected Rectangle pickupCollider;

    public Rectangle getPickupCollider() {
        return this.pickupCollider;
    }

    public Ingredient(Vector2 position, float speed, float weight, float decelSpeed, Texture texture, int id) {
        super(position, speed, weight, decelSpeed, texture);
        this.id = id;
        this.collider = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        this.pickupCollider = new Rectangle(position.x - (texture.getWidth() / 2), position.y - (texture.getHeight() / 2), texture.getWidth() + texture.getWidth(), texture.getHeight() + texture.getHeight());
        this.debugColor = Color.BLACK;
    }

    public void update(float delta) {
        super.update(delta);
        if(this.pickupCollider != null)
            this.pickupCollider.setPosition(new Vector2(this.position.x - (this.texture.getWidth() / 2), this.position.y - (this.texture.getHeight() / 2)));
    }

    public void draw(Batch batch, ShapeRenderer renderer) {
        super.draw(batch, renderer);
        if (Settings.DEBUG_COLLISION && this.pickupCollider != null) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.GRAY);
            renderer.rect(pickupCollider.x, pickupCollider.y, pickupCollider.width, pickupCollider.height);
            renderer.end();
        }
    }

    public void move(Vector2 position) {
        super.move(position);
    }
}
