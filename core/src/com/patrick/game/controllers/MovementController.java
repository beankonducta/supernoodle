package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

import java.util.List;

public class MovementController {

    private CollisionController collisionController;

    public MovementController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public void playerMove(Entity e) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            e.setVelocity(e.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            e.setVelocity(-e.getSpeed());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && e.getHeightGain() == 0) {
            e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) && e.getHeightGain() == 0) {
            e.setHeightGain(Settings.PLAYER_FALL_MOD);
        }
    }


    public void moveEntity(Entity e1, List<Entity> entities) {
        Rectangle temp = new Rectangle(e1.getPosition().x + e1.getVelocity(), e1.getPosition().y - e1.getWeight() + e1.getHeightGain(), e1.getCollider().width, e1.getCollider().height);
        // check if temp collides with any floors
        // if it does, calculate the distance between player and floor and force him to move only that far
        // if it doesn't, make the move!
    }
}
