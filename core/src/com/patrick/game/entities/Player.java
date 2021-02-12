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
        this.changeAnimation(this.currentAnim, true);
    }

    public void setHeightGain(float heightGain) {
        this.heightGain = heightGain;
        if(this.ingredient != null)
            this.ingredient.setHeightGain(heightGain * .75f);
    }

    public void changeAnimation(String anim, boolean force) {
        if(this.currentAnim != anim || force) {
            this.textureRegions = Resources.GET_PLAYER_TEXTURE_REGION(anim, this.getIngredient() != null);
            this.animation = Resources.GET_PLAYER_ANIMATION(anim, this.getIngredient() != null);
            this.currentAnim = anim;
        }
    }

    public Player(Vector2 position, float speed, float weight, float decelSpeed, int playerNumber) {
        super(position, speed, weight, decelSpeed, null);
        this.id = playerNumber;
        this.collider = new Rectangle(this.x() + Settings.TILE_SIZE, this.y(), 2, Settings.TILE_SIZE );
        this.floorCollider = new Rectangle(this.x() - Settings.TILE_SIZE, this.y(), Settings.TILE_SIZE * 2, Settings.TILE_SIZE);
        this.debugColor = Color.RED;
        this.changeAnimation("STILL", false);
    }
 
    public void update(float delta) {
        super.update(delta);
        if(this.grounded) this.heightGain = 0;
        if(this.grounded && Math.abs(this.velocity) == 0) this.changeAnimation("STILL", false);
        if(this.grounded && Math.abs(this.velocity) != 0) this.changeAnimation("RUN", false);
        if(!this.grounded && this.heightGain >= this.weight) this.changeAnimation("JUMP", false);
        if(!this.grounded && this.heightGain < this.weight)this.changeAnimation("FALL", false);
        if (this.collider != null)
            this.collider.setPosition(new Vector2(this.x() + Settings.TILE_SIZE, this.y()));
        if(this.floorCollider != null)
            this.floorCollider.setPosition(new Vector2(this.x(), this.y()));
    }

    public void move(Vector2 position) {
        super.move(position);
        if(this.ingredient != null) {
            this.ingredient.moveTo(new Vector2(this.getCollider().x - (this.width() * 3), this.getCollider().y + (this.height() * 2)));
        }
    }
}
