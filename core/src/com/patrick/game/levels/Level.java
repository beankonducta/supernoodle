package com.patrick.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Settings;

import java.util.List;

public class Level {

    private List<Entity> entities;

    public Level(List<Entity> entities) {
        this.entities = entities;
    }

    public void draw(Batch batch, ShapeRenderer renderer) {
        for (Entity e : entities) {
            e.draw(batch, renderer);
        }
    }

    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
    }
}
