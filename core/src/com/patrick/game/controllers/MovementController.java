package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Misc;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class MovementController {

    private CollisionController collisionController;
    private CameraController cameraController;
    private ParticleController particleController;

    private boolean canMove;
    private boolean processPhysics;

    private List<Entity> toRemove;
    private List<Entity> toAdd;

    public MovementController(CollisionController collisionController, CameraController cameraController, ParticleController particleController) {
        this.collisionController = collisionController;
        this.cameraController = cameraController;
        this.particleController = particleController;
        this.toRemove = new ArrayList<>();
        this.toAdd = new ArrayList<>();
        this.start();
    }

    public void start() {
        this.canMove = true;
        this.processPhysics = true;
    }

    public void stop() {
        this.canMove = false;
    }

    public void pause() {
        this.canMove = false;
        this.processPhysics = false;
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

    public void playerMove(Player p, Map map, float delta) {
        if (this.canMove) {
            final int[] KEYS = p.getId() == 1 ? Settings.PLAYER_ONE_KEYS : Settings.PLAYER_TWO_KEYS;
            if (KEYS != null) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) this.cameraController.zoomIn(delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) this.cameraController.zoomOut(delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) Settings.DEBUG_COLLISION = true;
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) Settings.DEBUG_COLLISION = false;
                if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    for (Bowl b : map.getBowls()) {
                        for (Ingredient i : map.getIngredients()) {
                            b.addIngredient(i);
                            this.toRemove.add(i);
                            if (b.getIngredientCount() == 5) return;
                        }
                    }
                }
            }
            if (Gdx.input.isKeyPressed(KEYS[0])) {
                if (p.getGrounded())
                    this.particleController.randomMoveParticlesAdd(map, p, 2);
                p.setVelocity(p.getSpeed());
                p.setDir(Direction.RIGHT);
            }
            if (Gdx.input.isKeyPressed(KEYS[1])) {
                if (p.getGrounded())
                    this.particleController.randomMoveParticlesAdd(map, p, 2);
                p.setVelocity(-p.getSpeed());
                p.setDir(Direction.LEFT);
            }
            if (Gdx.input.isKeyJustPressed(KEYS[2]) && p.getHeightGain() == 0 && p.getGrounded()) {
                p.setGrounded(false);
                p.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
                p.setDir(Direction.UP);
            }
            if (Gdx.input.isKeyJustPressed(KEYS[3]) && p.getHeightGain() == 0 && p.getGrounded()) {
                p.setGrounded(false);
                p.setHeightGain(-Settings.PLAYER_FALL_MOD);
            }
            if (Gdx.input.isKeyJustPressed(KEYS[4])) {
                if (p.getIngredient() != null) {
                    p.getIngredient().setHeightGain(p.getHeightGain() * 1.2f);
                    p.getIngredient().setVelocity(p.getVelocity() * 2f);
                    p.getIngredient().setHeld(false);
                    p.setIngredient(null);
                    p.update(delta);
                } else {
                    for (Ingredient i : map.getIngredients()) {
                        if (this.collisionController.checkIngredientPickupCollision(p, i))
                            this.attemptPickup(p, i);
                    }
                }
            }
            if (Gdx.input.isKeyPressed(KEYS[4])) {
                this.updatePlayerRemoveAction(p, map);
            }
            if (!Gdx.input.isKeyPressed(KEYS[4])) {
                if (p.getActionTimer() > 0) {
                    map.removeEffect(map.findEffectByParent(p));
                    p.resetActionTimer();
                }
            }
        }
        if (p.getIngredient() != null && (p.getVelocity() != 0))
            this.particleController.sweatParticlesAdd(map, p, 12);
        moveEntity(p, map, delta);
    }

    private void updatePlayerRemoveAction(Player p, Map map) {
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

    public void attemptPickup(Player p, Ingredient i) {
        if (p.getIngredient() == null) {
            i.setHeld(true);
            p.setIngredient(i);
        }
    }

    public void cloudMove(Cloud c, float delta) {
        if (!this.processPhysics) return;
        c.move(new Vector2(c.getSpeed() * delta, 0));
        if (c.x() < -c.width() * 2)
            c.moveTo(new Vector2(this.cameraController.getCamera().viewportWidth + Settings.TILE_SIZE, c.y()));
        if (c.x() > cameraController.getCamera().viewportWidth + Settings.TILE_SIZE)
            c.moveTo(new Vector2(-c.width() * 2, c.y()));
    }

    public void particleMove(Particle p, Map map, float delta) {
        if (!this.processPhysics) return;
        moveEntity(p, map, delta);
    }

    public void ingredientMove(Ingredient i, Map map, float delta) {
        if (!this.processPhysics) return;
        moveEntity(i, map, delta);
        this.attemptIngredientAdd(i, map);
    }

    public void moveEntity(Entity e, Map map, float delta) {
        if (!this.processPhysics) return;
        boolean didGround = false;
        if (e instanceof Ingredient) {
            Ingredient i = (Ingredient) e;
            // no need to calculate collisions if e is a held ingredient
            if (i.isHeld()) return;
        }
        e.move(new Vector2((e.getVelocity() * delta * (e.getGrounded() ? 1 : .5f)), ((e.getHeightGain() - e.getWeight()) * delta)));
        if (e instanceof Particle) {
            return;
        }
        if (e.x() < -Settings.TILE_SIZE / 2)
            e.moveTo(new Vector2(this.cameraController.getCamera().viewportWidth - Settings.TILE_SIZE, e.y()));
        if (e.x() > this.cameraController.getCamera().viewportWidth - Settings.TILE_SIZE)
            e.moveTo(new Vector2(-Settings.TILE_SIZE / 2, e.y()));
        for (Floor f : map.getFloors()) {
            if (this.collisionController.checkBasicFloorCollision(e, f, this.cameraController.getCamera().viewportWidth - Settings.TILE_SIZE)) {
                Vector2 offset = this.collisionController.calculateFloorCollisionOffset(e, f);
                e.move(new Vector2(0, offset.y));
                if (offset.x != 0) {
                    e.setVelocity(-e.getVelocity() * .95f);
                }
                if (offset.y > 0) {
                    if (!e.getGrounded()) {
                        this.particleController.randomMoveParticlesAdd(map, e, 12);
                    }
                    e.setGrounded(true);
                    didGround = true;
                }
                if (offset.y < 0) {
                    e.setHeightGain(e.getHeightGain() / 2);
                }
            }
        }
        if (!didGround) e.setGrounded(false);
        for (Entity ent : map.playersAndIngredients())
            if (e.getId() != ent.getId()) {
//                if (e instanceof Player && ent instanceof Player)
//                    if (this.collisionController.checkPlayerHeadBounceCollision((Player) e, (Player) ent)) {
//                        if (ent.y() >= e.y() + (ent.height() * .75f)) {
//                            ent.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
//                            these glitch player 1, because he's processing twice and negating the initial bounce
//                            e.setHeightGain(-Settings.PLAYER_FALL_MOD);
//                        } else if (ent.y() < e.y() + (ent.height() * .75f)) {
//                            e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
//                            ent.setHeightGain(-Settings.PLAYER_FALL_MOD);
//                        }
//                    }
                if (this.collisionController.checkBasicCollision(e, ent)) {
                    if (Math.abs(e.getVelocity()) > Math.abs(ent.getVelocity())) {
                        ent.move(new Vector2(e.getVelocity() * delta, 0));
                        ent.setVelocity(e.getVelocity());
                    } else if (Math.abs(ent.getVelocity()) > Math.abs(e.getVelocity())) {
                        e.move(new Vector2(ent.getVelocity() * delta, 0));
                        e.setVelocity(ent.getVelocity());
                    }
                }
            }
    }

    private void attemptIngredientAdd(Ingredient i, Map map) {
        if (i.isHeld()) return;
        for (Bowl b : map.getBowls()) {
            if (this.collisionController.checkBasicCollision(i, b)) {
                b.addIngredient(i);
                this.particleController.bowlParticlesAdd(map, b, 100);
                this.toRemove.add(i);
            }
        }
    }

    private void attemptIngredientRemove(Bowl b) {
        Entity e = b.removeLastIngredient();
        if (e != null) {
            e.moveTo(new Vector2(e.x(), e.y() + Settings.TILE_SIZE * 2));
            e.setHeightGain(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.PLAYER_JUMP_HEIGHT / 4), (int) (Settings.PLAYER_JUMP_HEIGHT)));
            e.setVelocity(com.patrick.game.util.Math.RANDOM_POS_NEG(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.INGREDIENT_SPEED), (int) (Settings.INGREDIENT_SPEED * 2))));
            this.toAdd.add(e);
        }
    }
}
