package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

public class MovementController {

    public MovementController() {

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
        moveEntity(e);
    }


    public void moveEntity(Entity e) {
        Vector2 previous = e.getPosition();
        e.move(new Vector2(e.getPosition().x + e.getVelocity(), e.getPosition().y - e.getWeight() + e.getHeightGain()));
    }
}
