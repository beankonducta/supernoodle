package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Settings;

public class Player extends AnimatedEntity {

    protected Ingredient ingredient;

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setHeightGain(float heightGain) {
        this.heightGain = heightGain;
        if(this.ingredient != null)
            this.ingredient.setHeightGain(heightGain * .75f);
    }

    public Player(Vector2 position, float speed, float weight, float decelSpeed, Texture texture, float animSpeed, int playerNumber) {
        super(position, speed, weight, decelSpeed, texture);
        this.textureRegions = Sprite.split(texture, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.animation = new Animation(animSpeed, this.textureRegions[0]);
        this.id = playerNumber;
        this.collider = new Rectangle(position.x, position.y, Settings.TILE_SIZE, Settings.TILE_SIZE);
        this.debugColor = Color.RED;
    }

    public void update(float delta) {
        super.update(delta);
        if(grounded) this.heightGain = 0;
    }

    public void move(Vector2 position) {
        super.move(position);
        // we might want to move ingredient out of this class
        if(this.ingredient != null) {
            this.ingredient.moveTo(new Vector2(this.getCollider().x, this.getCollider().y + this.getCollider().height + 5));
        }
    }
}
