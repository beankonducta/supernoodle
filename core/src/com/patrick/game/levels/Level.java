package com.patrick.game.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.patrick.game.entities.Entity;

public class Level {

    private Entity[] entities;

    public Level(Entity[] entities) {
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
