package com.patrick.game.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.*;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class LevelController {

    private CollisionController collisionController;
    private int fillCount1;
    private int fillCount2;
    private Map map;

    public int getFillCount(int id) {
        return id == -3 ? fillCount1 : fillCount2;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public LevelController(CollisionController collisionController, Map map) {
        this.collisionController = collisionController;
        this.map = map;
    }

    public void update(float delta) {
        this.removeEffectsAndParticles(delta);
    }

    public void draw(SuperNoodle game) {
        Color c = game.batch.getColor();
        int multiplier = this.map.getClouds().size() / 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < multiplier; j++) {
                game.batch.setColor(c.r, c.g, c.b, .5f);
                this.map.getClouds().get(i * j).draw(game.batch);
                game.batch.setColor(c.r, c.g, c.b, 1f);
            }
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

    public boolean checkFull(Bowl b) {
        return b.getIngredientCount() == 5;
    }

    public boolean checkWin(Bowl b) {
        return b.getId() == -3 ? this.fillCount1 == 3 : this.fillCount2 == 3;
    }

    public void increaseFillCount(Bowl b) {
        if (b.getId() == -3) this.fillCount1++;
        else this.fillCount2++;
        b.removeLastIngredient();
    }

    private void removeEffectsAndParticles(float delta) {
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