package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Settings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovementController {

    /*

    TODO: Fix collision and movement physics.

    TODO: I should move a lot of stuff out of here and into the game controller or something

     */

    private CollisionController collisionController;
    private CameraController cameraController;

    private List<Entity> toRemove;

    public MovementController(CollisionController collisionController, CameraController cameraController) {
        this.collisionController = collisionController;
        this.cameraController = cameraController;
        toRemove = new ArrayList<>();
    }

    public void updateEntityList(List<Entity> entities) {
        for (Entity e : toRemove) {
            entities.remove(e);
        }
        toRemove = new ArrayList<>();
    }


    public void playerMove(Entity e, List<Entity> entities, ShapeRenderer renderer, float delta) {
        final int[] KEYS = e.getId() == 1 ?
                new int[]{Input.Keys.D, Input.Keys.A, Input.Keys.W, Input.Keys.S, Input.Keys.CONTROL_LEFT} :
                new int[]{Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.CONTROL_RIGHT};
        if (e instanceof Player && KEYS != null) {
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
                    p.getIngredient().setHeightGain(p.getHeightGain() * 1.1f);
                    p.getIngredient().setVelocity(p.getVelocity() * .75f);
                    p.getIngredient().setHeld(false);
                    p.setIngredient(null);
                    p.update(delta);
                } else
                    for (Entity e1 : entities) {
                        if (e1 instanceof Ingredient) {
                            p.update(delta);
                            if (collisionController.checkIngredientPickupCollision(e, e1))
                                this.attemptPickup(e, e1);
                            else if (e1 instanceof Bowl)
                                if (collisionController.checkBasicCollision(e, e1))
                                    // this might error since we're currently iterating through the list when we call
                                    this.attemptIngredientRemove(e, entities);
                        }
                    }
            }
        }
        moveEntity(e, entities, renderer, delta);
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
        e.move(new Vector2(e.getSpeed() * delta, 0));
        if (e.getPosition().x < -e.getCollider().width)
            e.moveTo(new Vector2(cameraController.getCamera().viewportWidth, e.getPosition().y));
        if (e.getPosition().x > cameraController.getCamera().viewportWidth) e.moveTo(new Vector2(-e.getCollider().width, e.getPosition().y));
    }

    public void ingredientMove(Entity e, List<Entity> entities, ShapeRenderer renderer, float delta) {
        if (e instanceof Ingredient) {
            moveEntity(e, entities, renderer, delta);
            this.attemptIngredientAdd(e, entities);
        }
    }

    public void moveEntity(Entity e1, List<Entity> entities, ShapeRenderer renderer, float delta) {
        // no need to calculate collisions if e1 is a held ingredient
        if(e1 instanceof Ingredient) {
            Ingredient i = (Ingredient)e1;
            if(i.isHeld()) return;
        }
        e1.move(new Vector2((e1.getVelocity() * delta * (e1.getGrounded() ? 1 : .5f)), ((e1.getHeightGain() - e1.getWeight()) * delta)));
        if (e1.getPosition().x < 0)
            e1.moveTo(new Vector2(cameraController.getCamera().viewportWidth, e1.getPosition().y));
        if (e1.getPosition().x > cameraController.getCamera().viewportWidth) e1.moveTo(new Vector2(1, e1.getPosition().y));
        for (Entity e : entities) {
            if (e instanceof Floor) {
                if (collisionController.checkBasicCollision(e1, e)) {
                    Vector2 offset = collisionController.calculateFloorCollisionOffset(e1, e);
                    e1.move(new Vector2(0, offset.y));
                    if (offset.x != 0) {
                        e1.setVelocity(-e1.getVelocity() * .95f);
                    }
                    if (offset.y > 0)
                        e1.setGrounded(true);
                    if (offset.y < 0) {
                        e1.setHeightGain(e1.getHeightGain() / 2);
                    }
                    if (Settings.DEBUG_COLLISION) {
                        renderer.begin(ShapeRenderer.ShapeType.Line);
                        renderer.setColor(Color.WHITE);
                        renderer.rect(e.getCollider().x, e.getCollider().y, e.getCollider().width, e.getCollider().height);
                        renderer.end();
                    }
                }
            }
            if (e1.getId() != e.getId() && !(e instanceof Floor) && !(e instanceof Bowl) && !(e instanceof Cloud)) {
                if (collisionController.checkBasicCollision(e1, e)) {
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
                if (collisionController.checkBasicCollision(e1, e)) {
                    Bowl b = (Bowl) e;
                    b.addIngredient(i);
                    this.toRemove.add(i);
                }
        }
    }

    private void attemptIngredientRemove(Entity e1, List<Entity> entities) {
        Bowl b = (Bowl) e1;
        entities.add(b.removeLastIngredient());
    }
}
