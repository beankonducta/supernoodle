package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends AnimatedEntity {

    public Player(Vector2 position, float speed, float weight, float decelSpeed, Texture texture, int tileSize, float animSpeed, int playerNumber) {
        super(position, speed, weight, decelSpeed, texture);
        this.textureRegions = Sprite.split(texture, tileSize, tileSize);
        this.animation = new Animation(animSpeed, this.textureRegions[0]);
        this.id = playerNumber;
        this.collider = new Rectangle(position.x, position.y, tileSize, tileSize);
    }
}
