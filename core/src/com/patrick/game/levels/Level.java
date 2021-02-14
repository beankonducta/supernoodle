package com.patrick.game.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Map;
import com.patrick.game.util.Settings;

import java.util.List;

public class Level {

    private Map map;

    public Level(Map map) {
        this.map = map;
    }

    public void draw(SuperNoodle game) {
        for (Entity e : this.map.entities()) {
            e.draw(game.batch);
            if(Settings.DEBUG_ENTITIES) {
                game.font.draw(game.batch, ""+e.getId(), e.x(), e.y() + (e.height() * 1.2f));
            }
            if (Settings.DEBUG_COLLISION) {
                game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                game.shapeRenderer.setColor(Color.WHITE);
                game.shapeRenderer.rect(e.getCollider().x, e.getCollider().y, e.width(), e.height());
                game.shapeRenderer.end();
            }
        }
    }

    public void update(float delta) {
        for (Entity e : this.map.entities()) {
            e.update(delta);
        }
    }
}
