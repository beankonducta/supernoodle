package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
    private LevelController levelController;

    private boolean canMove;
    private boolean processPhysics;

    public MovementController(CollisionController collisionController, CameraController cameraController, ParticleController particleController, LevelController levelController) {
        this.collisionController = collisionController;
        this.cameraController = cameraController;
        this.particleController = particleController;
        this.levelController = levelController;
        this.start();
    }

    /**
     * Starts reading movement and processing physics.
     *
     */
    public void start() {
        this.canMove = true;
        this.processPhysics = true;
    }

    /**
     * Stops reading movements.
     *
     */
    public void stop() {
        this.canMove = false;
    }

    /**
     * Pauses reading movements and processing physics.
     *
     */
    public void pause() {
        this.canMove = false;
        this.processPhysics = false;
    }

    /**
     * Process user input and moves the player.
     *
     * @param p
     * @param map
     * @param delta
     */
    // TODO: Move the "player movement" to it's own method and the "user input" to it's own method
    public void playerMove(Player p, Map map, float delta) {
        // quit, for our arcade cabinet
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT_BRACKET) && Gdx.input.isKeyPressed(Input.Keys.RIGHT_BRACKET)) {
            System.exit(-1);
        }
        if (this.canMove) {
            final int[] KEYS = p.getId() == 1 ? Settings.PLAYER_ONE_KEYS : Settings.PLAYER_TWO_KEYS;
            if (KEYS != null) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) this.cameraController.zoomIn(delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) this.cameraController.zoomOut(delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) Settings.DEBUG_COLLISION = true;
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) Settings.DEBUG_COLLISION = false;
                // test win
                if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    for (Bowl b : map.getBowls()) {
                        for (Ingredient i : map.getIngredients()) {
                            b.addIngredient(i);
                            this.levelController.addToRemove(i);
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
                SoundController.playSound("jump");
            }
            if (Gdx.input.isKeyJustPressed(KEYS[3]) && p.getHeightGain() == 0 && p.getGrounded()) {
                p.setGrounded(false);
                p.setHeightGain(-Settings.PLAYER_FALL_MOD);
                SoundController.playSound("jump");
            }
            if (Gdx.input.isKeyJustPressed(KEYS[4])) {
                if (p.getIngredient() != null) {
                    this.levelController.removeIngredient(p, null);
                } else {
                    for (Ingredient i : map.getIngredients()) {
                        if (this.collisionController.checkIngredientPickupCollision(p, i))
                            this.levelController.attemptPickup(p, i);
                    }
                }
            }
            if (Gdx.input.isKeyPressed(KEYS[4])) {
                this.levelController.updatePlayerRemoveAction(p, map);
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

    /**
     * Processes cloud movement.
     *
     * @param c
     * @param delta
     */
    public void cloudMove(Cloud c, float delta) {
        if (!this.processPhysics) return;
        c.move(new Vector2(c.getSpeed() * delta, 0));
        if (c.x() < -c.width() * 2)
            c.moveTo(new Vector2(this.cameraController.getCamera().viewportWidth + Settings.TILE_SIZE, c.y()));
        if (c.x() > cameraController.getCamera().viewportWidth + Settings.TILE_SIZE)
            c.moveTo(new Vector2(-c.width() * 2, c.y()));
    }

    /**
     * Processes particle movement.
     *
     * @param p
     * @param map
     * @param delta
     */
    public void particleMove(Particle p, Map map, float delta) {
        if (!this.processPhysics) return;
        moveEntity(p, map, delta);
    }

    /**
     * Processes ingredient movement.
     *
     * @param i
     * @param map
     * @param delta
     */
    public void ingredientMove(Ingredient i, Map map, float delta) {
        if (!this.processPhysics) return;
        moveEntity(i, map, delta);
        this.levelController.attemptIngredientAdd(i, map, delta);
    }

    /**
     * Actually moves the entity. Calculates for screen looping and collisions.
     *
     * @param e
     * @param map
     * @param delta
     */
    public void moveEntity(Entity e, Map map, float delta) {
        if (!this.processPhysics) return;
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
        this.processPlayerPlayerCollisions(map, map.playerOne(), map.playerTwo());
        this.processEntityFloorCollisions(e, map, delta);
        this.processEntityEntityCollisions(e, map, delta);
    }

    /**
     * Handles a collision in which an entity is on the floor.
     *
     * @param e
     * @param map
     * @param delta
     */
    public void processEntityFloorCollisions(Entity e, Map map, float delta) {
        boolean didGround = false;
        for (Floor f : map.getFloors()) {
            if (this.collisionController.checkBasicFloorCollision(e, f, this.cameraController.getCamera().viewportWidth - Settings.TILE_SIZE)) {
                if (e instanceof Ingredient) {
                    Ingredient i = (Ingredient) e;
                    if (i.getHeightGain() == 0 && i.getGrounded() && i.wasHeld()) {
                        i.setWasHeld(false);
                        e.setVelocity(e.getVelocity() * .5f);
                    }
                }
                final Vector2 offset = this.collisionController.calculateFloorCollisionOffset(e, f);
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
    }

    /**
     * Handles a collision in which an entity runs into another non floor entity (only movable entities apply here).
     *
     * @param e
     * @param map
     * @param delta
     */
    public void processEntityEntityCollisions(Entity e, Map map, float delta) {
        for (Entity ent : map.playersAndIngredients())
            if (e.getId() != ent.getId()) {
                if (this.collisionController.checkBasicCollision(e, ent)) {
                    if (Math.abs(e.getVelocity()) > Math.abs(ent.getVelocity())) {
                        ent.move(new Vector2(e.getVelocity() * delta / 3, 0));
                        ent.setVelocity(e.getVelocity());
                        SoundController.playSound("jump");
                    } else if (Math.abs(ent.getVelocity()) > Math.abs(e.getVelocity())) {
                        e.move(new Vector2(ent.getVelocity() * delta / 3, 0));
                        e.setVelocity(ent.getVelocity());
                    }
                }
            }
    }

    /**
     * Processes player to player collisions, specifically to handle physics between the players including
     * 'bouncing' off of each other, forcing ingredient drops, etc.
     *
     * @param p1
     * @param p2
     */
    public void processPlayerPlayerCollisions(Map map, Player p1, Player p2) {
        if (this.collisionController.checkPlayerHeadBounceCollision(p1, p2)) {
            SoundController.playSoundOffset("jump", .1f);
            if (p1.y() >= p2.y() || Math.abs(p1.getVelocity()) > Math.abs(p2.getVelocity())) {
                p1.setHeightGain(Settings.PLAYER_JUMP_HEIGHT * .8f);
                p1.setGrounded(false);
                this.particleController.sweatParticlesAdd(map, p2, 5);
                if (p2.y() > this.cameraController.getCamera().viewportHeight / 10)
                    p2.setHeightGain(-Settings.PLAYER_FALL_MOD);
            } else if (p1.y() < p2.y() | Math.abs(p2.getVelocity()) > Math.abs(p1.getVelocity())) {
                p2.setHeightGain(Settings.PLAYER_JUMP_HEIGHT * .8f);
                p2.setGrounded(false);
                this.particleController.sweatParticlesAdd(map, p1, 5);
                if (p1.y() > this.cameraController.getCamera().viewportHeight / 10)
                    p1.setHeightGain(-Settings.PLAYER_FALL_MOD);
            }
            if (Math.abs(p1.getVelocity()) > Math.abs(p2.getVelocity())) {
                this.levelController.removeIngredient(p2, p1);
            } else {
                this.levelController.removeIngredient(p1, p2);
            }
        }
    }
}
