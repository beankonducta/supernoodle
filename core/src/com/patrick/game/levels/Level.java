package com.patrick.game.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.entities.Entity;

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
        }
    }
}
