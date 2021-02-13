package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Misc;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class MovementController {

    private CollisionController collisionController;
    private CameraController cameraController;

    private boolean canMove;
    private boolean processPhysics;

    private List<Entity> toRemove;
    private List<Entity> toAdd;

    public MovementController(CollisionController collisionController, CameraController cameraController) {
        this.collisionController = collisionController;
        this.cameraController = cameraController;
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

    public void updateEntityList(List<Entity> entities) {
        for (Entity e : this.toRemove) {
            entities.remove(e);
        }
        this.toRemove = new ArrayList<>();
        for (Entity e : this.toAdd) {
            entities.add(e);
        }
        this.toAdd = new ArrayList<>();
    }

    public void playerMove(Entity e, List<Entity> entities, float delta) {
        if (this.canMove) {
            final int[] KEYS = e.getId() == 1 ?
                    new int[]{Input.Keys.D, Input.Keys.A, Input.Keys.W, Input.Keys.S, Input.Keys.CONTROL_LEFT} :
                    new int[]{Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.CONTROL_RIGHT};
            if (e instanceof Player && KEYS != null) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) this.cameraController.zoomIn(delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) this.cameraController.zoomOut(delta);
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) Settings.DEBUG_COLLISION = true;
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) Settings.DEBUG_COLLISION = false;
                if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    for (Entity e1 : entities) {
                        if (e1 instanceof Bowl) {
                            for (Entity e2 : entities) {
                                if (e2 instanceof Ingredient) {
                                    ((Bowl) e1).addIngredient((Ingredient) e2);
                                    this.toRemove.add(e2);
                                    if (((Bowl) e1).getIngredientCount() == 5) return;
                                }
                            }
                        }
                    }
                }
                if (Gdx.input.isKeyPressed(KEYS[0])) {
                    e.setVelocity(e.getSpeed());
                    e.setDir(Direction.RIGHT);
                }
                if (Gdx.input.isKeyPressed(KEYS[1])) {
                    e.setVelocity(-e.getSpeed());
                    e.setDir(Direction.LEFT);
                }
                if (Gdx.input.isKeyJustPressed(KEYS[2]) && e.getHeightGain() == 0 && e.getGrounded()) {
                    e.setGrounded(false);
                    e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
                    e.setDir(Direction.UP);
                }
                if (Gdx.input.isKeyJustPressed(KEYS[3]) && e.getHeightGain() == 0 && e.getGrounded()) {
                    e.setGrounded(false);
                    e.setHeightGain(-Settings.PLAYER_FALL_MOD);
                }
                if (Gdx.input.isKeyJustPressed(KEYS[4])) {
                    Player p = (Player) e;
                    if (p.getIngredient() != null) {
                        p.getIngredient().setHeightGain(p.getHeightGain() * 1.2f);
                        p.getIngredient().setVelocity(p.getVelocity() * 2f);
                        p.getIngredient().setHeld(false);
                        p.setIngredient(null);
                        p.update(delta);
                    } else {
                        for (Entity e1 : entities) {
                            if (e1 instanceof Ingredient) {
                                if (this.collisionController.checkIngredientPickupCollision(e, e1))
                                    this.attemptPickup(e, e1);
                            } else if (e1 instanceof Bowl) {
                                // action timer is NOT working (it's because we have two action keys)
                                if (this.collisionController.checkPlayerBowlCollision(e, e1)) {
                                    if (Misc.PLAYER_BOWL_MATCH(e, e1)) {
                                        e.incrementActionTimer();
                                        if (e.getActionTimer() >= 1) {
                                            this.attemptIngredientRemove(e1);
                                            e.resetActionTimer();
                                        }
                                    }
                                    e.resetActionTimer();
                                }
                            }
                        }
                    }
                }
            }
        }
        moveEntity(e, entities, delta);
    }

    public void attemptPickup(Entity e1, Entity e2) {
        if (e1 instanceof Player && e2 instanceof Ingredient) {
            Player p = (Player) e1;
            if (p.getIngredient() == null) {
                Ingredient i = (Ingredient) e2;
                i.setHeld(true);
                p.setIngredient(i);
            }
        }
    }

    public void cloudMove(Entity e, float delta) {
        if (!this.processPhysics) return;
        e.move(new Vector2(e.getSpeed() * delta, 0));
        if (e.x() < -e.width() * 2)
            e.moveTo(new Vector2(this.cameraController.getCamera().viewportWidth + Settings.TILE_SIZE, e.y()));
        if (e.x() > cameraController.getCamera().viewportWidth + Settings.TILE_SIZE)
            e.moveTo(new Vector2(-e.width() * 2, e.y()));
    }

    public void ingredientMove(Entity e, List<Entity> entities, float delta) {
        if (!this.processPhysics) return;
        if (e instanceof Ingredient) {
            moveEntity(e, entities, delta);
            this.attemptIngredientAdd(e, entities);
        }
    }

    public void moveEntity(Entity e1, List<Entity> entities, float delta) {
        if (!this.processPhysics) return;
        boolean didGround = false;
        // no need to calculate collisions if e1 is a held ingredient
        if (e1 instanceof Ingredient) {
            Ingredient i = (Ingredient) e1;
            if (i.isHeld()) return;
        }
        e1.move(new Vector2((e1.getVelocity() * delta * (e1.getGrounded() ? 1 : .5f)), ((e1.getHeightGain() - e1.getWeight()) * delta)));
        if (e1.x() < 0)
            e1.moveTo(new Vector2(this.cameraController.getCamera().viewportWidth, e1.y()));
        if (e1.x() > this.cameraController.getCamera().viewportWidth)
            e1.moveTo(new Vector2(1, e1.y()));
        for (Entity e : entities) {
            if (e instanceof Floor) {
                if (this.collisionController.checkBasicFloorCollision(e1, e)) {
                    Vector2 offset = this.collisionController.calculateFloorCollisionOffset(e1, e);
                    e1.move(new Vector2(0, offset.y));
                    if (offset.x != 0) {
                        e1.setVelocity(-e1.getVelocity() * .95f);
                    }
                    if (offset.y > 0) {
                        e1.setGrounded(true);
                        didGround = true;
                    }
                    if (offset.y < 0) {
                        e1.setHeightGain(e1.getHeightGain() / 2);
                    }
                }
            }
            if (!didGround) e1.setGrounded(false);
            if (e1.getId() != e.getId() && !(e instanceof Floor) && !(e instanceof Bowl) && !(e instanceof Cloud)) {
                if (e1 instanceof Player && e instanceof Player)
                    if (this.collisionController.checkPlayerHeadBounceCollision((Player) e1, (Player) e)) {
                        System.out.println(e1.getId() + " : " + e.getId());
                        if (e.y() >= e1.y() + (e1.height() * .75f)) {
                            e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
                            // these glitch player 1, because he's processing twice and negating the initial bounce
//                            e1.setHeightGain(-Settings.PLAYER_FALL_MOD);
                        }
                        else if (e.y() < e1.y() + (e.height() * .75f)) {
                            e1.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
//                            e.setHeightGain(-Settings.PLAYER_FALL_MOD);
                        }
                    }
                if (this.collisionController.checkBasicCollision(e1, e)) {
                    if (Math.abs(e1.getVelocity()) > Math.abs(e.getVelocity())) {
                        e.move(new Vector2(e1.getVelocity() * delta, 0));
                        e.setVelocity(e1.getVelocity());
                    } else if (Math.abs(e.getVelocity()) > Math.abs(e1.getVelocity())) {
                        e1.move(new Vector2(e.getVelocity() * delta, 0));
                        e1.setVelocity(e.getVelocity());
                    }
                }
            }
        }
    }

    private void attemptIngredientAdd(Entity e1, List<Entity> entities) {
        Ingredient i = (Ingredient) e1;
        for (Entity e : entities) {
            if (e instanceof Bowl)
                if (this.collisionController.checkBasicCollision(e1, e)) {
                    Bowl b = (Bowl) e;
                    b.addIngredient(i);
                    this.toRemove.add(i);
                }
        }
    }

    private void attemptIngredientRemove(Entity e1) {
        Bowl b = (Bowl) e1;
        Entity e = b.removeLastIngredient();
        if (e != null) {
            e.moveTo(new Vector2(e.x(), e.y() + Settings.TILE_SIZE * 2));
            e.setHeightGain(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.PLAYER_JUMP_HEIGHT / 4), (int) (Settings.PLAYER_JUMP_HEIGHT)));
            e.setVelocity(com.patrick.game.util.Math.RANDOM_POS_NEG(com.patrick.game.util.Math.RANDOM_BETWEEN((int) (Settings.INGREDIENT_SPEED), (int) (Settings.INGREDIENT_SPEED * 2))));
            this.toAdd.add(e);
        }
    }
}
