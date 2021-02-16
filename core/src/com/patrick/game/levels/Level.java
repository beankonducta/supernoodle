package com.patrick.game.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.Effect;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Map;
import com.patrick.game.entities.Particle;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private Map map;

    public Level(Map map) {
        this.map = map;
    }

    public void draw(SuperNoodle game) {
        int cloudCount = 0;
        int multiplier = this.map.getClouds().size() / 5;
        for (int i = 0; i < 5; i++) {
            for(int j = 1; j < multiplier; j++)
                this.map.getClouds().get(i * j).draw(game.batch);
            game.batch.draw(Resources.MOUNTAIN(i), 0, 0);
        }
        for (Entity e : this.map.entitiesWithoutClouds()) {
            e.draw(game.batch);
            if (Settings.DEBUG_ENTITIES) {
                game.font.draw(game.batch, "" + e.getId(), e.x(), e.y() + (e.height() * 1.2f));
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
        List<Effect> effectsToRemove = new ArrayList<>();
        List<Particle> particlesToRemove = new ArrayList<>();
        for (Entity e : this.map.entities()) {
            e.update(delta);
        }
        for (Effect e : this.map.getEffects()) {
            if (e.isDone()) effectsToRemove.add(e);
        }
        for (Particle p : this.map.getParticles()) {
            if (p.getTimeToLive() <= 0) particlesToRemove.add(p);
        }
        for (Effect e : effectsToRemove) {
            this.map.removeEffect(e);
        }
        for (Particle p : particlesToRemove) {
            this.map.removeParticle(p);
        }
    }
}