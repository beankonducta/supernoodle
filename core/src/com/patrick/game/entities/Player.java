package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class Player extends AnimatedEntity {

    protected Ingredient ingredient;
    protected String currentAnim;

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

    public void changeAnimation(String anim) {
        if(this.currentAnim != anim) {
            this.textureRegions = Resources.GET_PLAYER_TEXTURE_REGION(anim, this.getIngredient() != null);
            this.animation = Resources.GET_PLAYER_ANIMATION(anim, this.getIngredient() != null);
            this.currentAnim = anim;
        }
    }

    public Player(Vector2 position, float speed, float weight, float decelSpeed, int playerNumber) {
        super(position, speed, weight, decelSpeed, null);
        this.id = playerNumber;
        // this ain't updating correctly because our super class is moving the collider back to pos.x and pos.y (excluding the offset here)
        this.collider = new Rectangle(position.x + (Settings.TILE_SIZE * .25f), position.y, Settings.TILE_SIZE * .5f, Settings.TILE_SIZE);
        this.debugColor = Color.RED;
        this.changeAnimation("STILL");
    }

    public void update(float delta) {
        super.update(delta);
        if(grounded) this.heightGain = 0;
        if(grounded && Math.abs(this.velocity) == 0) this.changeAnimation("STILL");
        if(grounded && Math.abs(this.velocity) != 0) this.changeAnimation("RUN");
        if(!grounded && this.heightGain >= this.weight) this.changeAnimation("JUMP");
        if(!grounded && this.heightGain < this.weight)this.changeAnimation("FALL");
    }

    public void move(Vector2 position) {
        super.move(position);
        // we might want to move ingredient out of this class
        if(this.ingredient != null) {
            this.ingredient.moveTo(new Vector2(this.getCollider().x, this.getCollider().y + this.getCollider().height + 5));
        }
    }
}
