package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends AnimatedEntity {

    enum Direction {
        LEFT, RIGHT
    }

    private Direction dir;

    public Player(Vector2 position, float speed, float velocity, Texture texture, int tileSize, float animSpeed) {
        super(position, speed, velocity, texture);
        this.textureRegions = Sprite.split(texture, tileSize, tileSize);
        this.animation = new Animation(animSpeed, this.textureRegions[0]);
        System.out.println(this.textureRegions.length);
    }
}
