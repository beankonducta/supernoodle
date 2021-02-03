package com.patrick.game.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.entities.Entity;

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
