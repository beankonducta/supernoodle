package com.patrick.game.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.*;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Misc;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class LevelController {

    private CollisionController collisionController;
    private ParticleController particleController;
    private int fillCount1;
    private int fillCount2;
    private Map map;

    private List<Entity> toRemove;
    private List<Entity> toAdd;

    public void addToRemove(Entity e) {
        if (this.toRemove == null) return;
        this.toRemove.add(e);
    }

    public void addToAdd(Entity e) {
        if (this.toAdd == null) return;
        this.toAdd.add(e);
    }

    public int getFillCount(int id) {
        return id == -3 ? fillCount1 : fillCount2;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public LevelController(CollisionController collisionController, ParticleController particleController, Map map) {
        this.collisionController = collisionController;
        this.particleController = particleController;
        this.map = map;
        this.toRemove = new ArrayList<>();
        this.toAdd = new ArrayList<>();
    }

    public void update(float delta) {
        this.removeEffectsAndParticles(delta);
        this.updateEntityList(this.map);
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

    public void attemptIngredientAdd(Ingredient i, Map map) {
        if (i.isHeld()) return;
        for (Bowl b : map.getBowls()) {
            if (this.collisionController.checkBasicCollision(i, b)) {
                b.addIngredient(i);
                this.particleController.bowlParticlesAdd(map, b, 100);
                this.addToRemove(i);
            }
        }
    }

    public void attemptIngredientRemove(Bowl b) {
        Entity e = b.removeLastIngredient();
        if (e != null) {
            e.moveTo(new Vector2(e.x(), e.y() + Settings.TILE_SIZE * 2));
            e.setHeightGain(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.PLAYER_JUMP_HEIGHT / 4), (int) (Settings.PLAYER_JUMP_HEIGHT)));
            e.setVelocity(com.patrick.game.util.Math.RANDOM_POS_NEG(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.INGREDIENT_SPEED), (int) (Settings.INGREDIENT_SPEED * 2))));
            this.addToAdd(e);
        }
    }

    public void attemptPickup(Player p, Ingredient i) {
        if (p.getIngredient() == null) {
            i.setHeld(true);
            p.setIngredient(i);
        }
    }

    public void updatePlayerRemoveAction(Player p, Map map) {
        for (Bowl b : map.getBowls()) {
            if (this.collisionController.checkPlayerBowlCollision(p, b)) {
                if (!Misc.PLAYER_BOWL_MATCH(p, b) && b.getIngredientCount() > 0) {
                    if (p.getActionTimer() == 0)
                        map.addEffect(new Effect((new Vector2(p.x() + (Settings.TILE_SIZE / 2), p.y() + p.height() + (Settings.TILE_SIZE * 1.5f))), Resources.TIMER_ANIMATION, Resources.TIMER_REGION, p.getId(), 90));
                    p.incrementActionTimer();
                    if (p.getActionTimer() >= 50) {
                        this.attemptIngredientRemove(b);
                        p.resetActionTimer();
                    }
                }
            }
        }
    }

    /**
     * P1 is holding the ingredient, p2 is optional for player player collision calculations.
     *
     * @param p1
     * @param p2
     */
    public void removeIngredient(Player p1, Player p2) {
        if (p1.getIngredient() == null) return;
        final float velocity = p2 == null ? p1.getVelocity() : p2.getVelocity();
        final Direction direction = p2 == null ? p1.getDir() : p2.getDir();
        p1.getIngredient().setHeightGain(p1.getHeightGain() * 1.2f);
        p1.getIngredient().setVelocity(velocity * 2f);
        if (p1.getGrounded() && p1.getHeightGain() == 0)
            p1.getIngredient().move(new Vector2((direction == Direction.LEFT ? -10 : 10), 0));
        p1.getIngredient().setHeld(false);
        p1.setIngredient(null);
    }

    public void updateEntityList(Map map) {
        for (Entity e : this.toRemove) {
            map.getIngredients().remove(e);
        }
        this.toRemove = new ArrayList<>();
        for (Entity e : this.toAdd) {
            map.getIngredients().add((Ingredient) e);
        }
        this.toAdd = new ArrayList<>();
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