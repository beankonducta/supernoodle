package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    public Player(Vector2 position, float speed, float weight, float decelSpeed, Texture texture, int tileSize, float animSpeed, int playerNumber) {
        super(position, speed, weight, decelSpeed, texture);
        this.textureRegions = Sprite.split(texture, tileSize, tileSize);
        this.animation = new Animation(animSpeed, this.textureRegions[0]);
        this.id = playerNumber;
        this.collider = new Rectangle(position.x, position.y, tileSize, tileSize);
    }

    public void move(Vector2 position) {
        super.move(position);
        // we might want to move ingredient out of this class
        if(this.ingredient != null) {
            this.ingredient.move(position);
        }
    }
}
