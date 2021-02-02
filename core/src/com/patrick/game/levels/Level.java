package com.patrick.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Settings;

import java.util.List;

public class Level {

    private List<Entity> entities;

    public Level(List<Entity> entities) {
        this.entities = entities;
    }

    public void draw(Batch batch) {
        for(Entity e : entities) {
            e.draw(batch);
        }
    }

    public void update() {
        for(Entity e : entities) {
            e.update();
            this.checkMove(e);
        }
    }

    // i'm thinking this should be in 'gamescreen' not 'level'
    // level should be the actual rendered level, not the controls
    public void checkMove(Entity e) {
        if(e instanceof Player && e.getId() == 1) {
            if(Gdx.input.isKeyPressed(Input.Keys.D)) {
                e.move(new Vector2(e.getSpeed(), 0));
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                e.move(new Vector2(-e.getSpeed(), 0));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.W) && e.getHeightGain() == 0) {
                e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.S) && e.getHeightGain() == 0) {
                e.setHeightGain(Settings.PLAYER_FALL_MOD);
            }
        }
    }
}
