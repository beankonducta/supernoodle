package com.patrick.game.levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

import java.util.List;

public class Level {

    private List<Entity> entities;

    public Level(List<Entity> entities) {
        this.entities = entities;
    }

    public void draw(SuperNoodle game) {
        for (Entity e : entities) {
            e.draw(game.batch, game.shapeRenderer);
            if(Settings.DEBUG_ENTITIES) {
                game.font.draw(game.batch, ""+e.getId(), e.getPosition().x, e.getPosition().y + (e.getCollider().height * 1.2f));

            }
        }
    }

    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
    }
}
