package com.patrick.game.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.*;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Math;
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

    /**
     * Adds one entity to the toRemove list.
     *
     * @param e
     */
    public void addToRemove(Entity e) {
        if (this.toRemove == null) return;
        this.toRemove.add(e);
    }

    /**
     * Adds one entity to the toAdd list.
     *
     * @param e
     */
    public void addToAdd(Entity e) {
        if (this.toAdd == null) return;
        this.toAdd.add(e);
    }

    /**
     * Updates the level.
     *
     * @param delta
     */
    public void update(float delta) {
        this.removeEffectsAndParticles(delta);
        this.updateEntityList(this.map);
    }

    /**
     * Draws the level.
     *
     * @param game
     */
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

    /**
     * Returns whether or not we have a full bowl, based on provided bowl.
     *
     * @param b
     * @return
     */
    public boolean checkFull(Bowl b) {
        return b.getIngredientCount() == 5;
    }

    /**
     * Returns whether or not we have a winner, based on provided bowl.
     *
     * @param b
     * @return
     */
    public boolean checkWin(Bowl b) {
        return b.getId() == -3 ? this.fillCount1 == 3 : this.fillCount2 == 3;
    }

    /**
     * Increases the local fillCount variable based on provided bowl's id.
     *
     * @param b
     */
    public void increaseFillCount(Bowl b) {
        if (b.getId() == -3) this.fillCount1++;
        else this.fillCount2++;
    }

    /**
     * Attempts to add an ingredient to a bowl.
     *
     * @param i
     * @param map
     */
    public void attemptIngredientAdd(Ingredient i, Map map, float delta) {
        if (i.isHeld()) return;
        for (Bowl b : map.getBowls()) {
            // "draws" the ingredient into the bowl, if it's near by
            if (this.collisionController.checkIngredientBowlNearby(b, i)) {
                i.move(new Vector2((b.x() - i.x()) * delta / 5, 0));
            }
            if (this.collisionController.checkBasicCollision(i, b)) {
                b.addIngredient(i);
                this.particleController.bowlParticlesAdd(map, b, 100);
                this.addToRemove(i);
                SoundController.playSound("bowl add");
            }
        }
    }

    /**
     * Attempts to remove the last ingredient from a bowl.
     *
     * @param b
     */
    public void attemptIngredientRemove(Bowl b, Player p) {
        Entity e = b.removeLastIngredient();
        if (e != null && p.getIngredient() == null) {
//            e.moveTo(new Vector2(e.x() + com.patrick.game.util.Math.EITHER_OR(-Settings.TILE_SIZE, Settings.TILE_SIZE), e.y() + Settings.TILE_SIZE));
//            e.setHeightGain(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.PLAYER_JUMP_HEIGHT / 2), (int) (Settings.PLAYER_JUMP_HEIGHT)));
//            e.setVelocity(com.patrick.game.util.Math.RANDOM_POS_NEG(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.INGREDIENT_SPEED * 2), (int) (Settings.INGREDIENT_SPEED * 4))));
            this.addToAdd(e);
            this.attemptPickup(p, (Ingredient) e);
            SoundController.playSound("bowl remove");
        }
    }

    /**
     * Attempts to let player pick up ingredient.
     *
     * @param p
     * @param i
     */
    public void attemptPickup(Player p, Ingredient i) {
        if (p.getIngredient() == null) {
            i.setHeld(true);
            p.setIngredient(i);
            SoundController.playSound("pickup");
        }
    }

    /**
     * Updates the action timer on provided player, so long as they're colliding with something that has an action.
     *
     * @param p
     * @param map
     */
    public void updatePlayerRemoveAction(Player p, Map map) {
        for (Bowl b : map.getBowls()) {
            if (this.collisionController.checkPlayerBowlCollision(p, b)) {
                if (!Misc.PLAYER_BOWL_MATCH(p, b) && b.getIngredientCount() > 0) {
                    this.particleController.sweatParticlesAdd(map, p, 12);
                    if (p.getActionTimer() == 0)
                        map.addEffect(new Effect((new Vector2(p.x() + (Settings.TILE_SIZE * .75f), p.y() + p.height() + (Settings.TILE_SIZE * 1.5f))), Resources.TIMER_ANIMATION, Resources.TIMER_REGION, p.getId(), 90));
                    p.incrementActionTimer();
                    if (p.getActionTimer() >= 40) {
                        this.attemptIngredientRemove(b, p);
                        p.resetActionTimer();
                    }
                }
            }
        }
    }

    /**
     * Removes ingredient from player's hands. P2 is used for optional player player collision checks,
     * in which case the removed ingredient gains it's velocity and height gain from the player
     * running into the other one.
     *
     * @param p1
     * @param p2
     */
    public void removeIngredient(Player p1, Player p2) {
        if (p1.getIngredient() == null) return;
        final float velocity = p2 == null ? p1.getVelocity() : p2.getVelocity();
        final Direction direction = p2 == null ? p1.getDir() : p2.getDir();
        /**
         * TODO:
         * I need to fix these physics. It doesn't really 'throw' the ingredient, rather kind of floats it up.
         *
         * There's got to be some actual math I can use here to make it 'arch' more like it would in real life.
         */
        p1.getIngredient().setHeightGain(p1.getHeightGain());
        p1.getIngredient().setVelocity(velocity);
        if (p1.getGrounded() && p1.getHeightGain() == 0)
            p1.getIngredient().move(new Vector2((direction == Direction.LEFT ? -10 : 10), 0));
        p1.getIngredient().setHeld(false);
        p1.setIngredient(null);
        SoundController.playSound("pickup");
    }

    /**
     * Updates the active map's entity list with the values queued up in our toAdd and toRemove
     * lists.
     *
     * @param map
     */
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

    /**
     * Updates the active map's particle and effects list by removing any 'dead' particles and any
     * 'finished' effects.
     *
     * @param delta
     */
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