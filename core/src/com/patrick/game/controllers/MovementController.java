package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;
import com.patrick.game.util.Settings;

import java.util.List;

public class MovementController {

    /*
    TODO:

    KNOWN BUG:

    When the player moves LEFT, then UP, they teleport through the wall. totally edge case, doesn't seem to happen anywhere
    else.

    REFACTOR:

    I should move a lot of stuff out of here and into the game controller or something

     */

    private CollisionController collisionController;

    public MovementController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public void playerMove(Entity e, List<Entity> entities, ShapeRenderer renderer, float delta) {
        final int[] KEYS = e.getId() == 1 ?
                new int[]{Input.Keys.D, Input.Keys.A, Input.Keys.W, Input.Keys.S, Input.Keys.CONTROL_LEFT} :
                new int[]{Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.CONTROL_RIGHT};
        if (e instanceof Player && KEYS != null) {
            if (Gdx.input.isKeyPressed(KEYS[0])) {
                e.setVelocity(e.getSpeed());
            }
            if (Gdx.input.isKeyPressed(KEYS[1])) {
                e.setVelocity(-e.getSpeed());
            }
            if (Gdx.input.isKeyJustPressed(KEYS[2]) && e.getHeightGain() == 0 && e.getGrounded()) {
                e.setGrounded(false);
                e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
            }
            if (Gdx.input.isKeyJustPressed(KEYS[3]) && e.getHeightGain() == 0 && e.getGrounded()) {
                e.setGrounded(false);
                e.setHeightGain(-Settings.PLAYER_FALL_MOD);
            }
            if (Gdx.input.isKeyJustPressed(KEYS[4])) {
                Player p = (Player) e;
                if (p.getIngredient() != null) p.setIngredient(null);
                else
                    for (Entity e1 : entities) {
                        if (e1 instanceof Ingredient)
                            if (collisionController.checkIngredientPickupCollision(e, e1))
                                this.attemptPickup(e, e1);
                            else if (e1 instanceof Bowl)
                                if (collisionController.checkBasicCollision(e, e1))
                                    // this might error since we're currently iterating through the list when we call
                                    this.attemptIngredientRemove(e, entities);
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
                i.setOffset(new Vector2(-8, 16));
                p.setIngredient(i);
            }
        }
    }

    public void ingredientMove(Entity e, List<Entity> entities, ShapeRenderer renderer, float delta) {
        if (e instanceof Ingredient) {
            moveEntity(e, entities, renderer, delta);
        }
    }

    public void moveEntity(Entity e1, List<Entity> entities, ShapeRenderer renderer, float delta) {
        e1.move(new Vector2((e1.getVelocity() * delta * (e1.getGrounded() ? 1 : .5f)), ((e1.getHeightGain() - e1.getWeight()) * delta)));
        for (Entity e : entities) {
            if (e instanceof Floor) {
                if (collisionController.checkBasicCollision(e1, e)) {
                    Vector2 offset = collisionController.calculateFloorCollisionOffset(e1, e);
                    e1.move(offset);
                    if (offset.x != 0) {
                        e1.setVelocity(-e1.getVelocity()*.95f);
                        break;
                    }
                    if (offset.y > 0)
                        e1.setGrounded(true);
                    if(offset.y < 0) {
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
            if (e1.getId() != e.getId() && !(e instanceof Floor) && !(e instanceof Bowl)) {
                if (collisionController.checkBasicCollision(e1, e)) {
                    if (Math.abs(e1.getVelocity()) > Math.abs(e.getVelocity())) {
                        e.move(new Vector2(e1.getVelocity() * delta, 0));
                        e.setVelocity(e1.getVelocity() * .75f * delta);
                    } else if(Math.abs(e.getVelocity()) > Math.abs(e1.getVelocity())) {
                        e1.move(new Vector2(e.getVelocity() * delta, 0));
                        e1.setVelocity(e.getVelocity() * .75f * delta);
                    }
                }
            }
        }
        // try to add ingredient to bowl after it's moved
        if (e1 instanceof Ingredient)
            this.attemptIngredientAdd(e1, entities);
    }

    private void attemptIngredientAdd(Entity e1, List<Entity> entities) {
        Ingredient i = (Ingredient) e1;
        boolean rm = false;
        for (Entity e : entities) {
            if (e instanceof Bowl)
                if (collisionController.checkBasicCollision(i.getPickupCollider(), e.getCollider())) {
                    Bowl b = (Bowl) e;
                    b.addIngredient(i);
                    System.out.println("Ingredient Added to Bowl");
                    rm = true;
                }
        }
//        if(rm)
//        entities.remove(e1);
    }

    private void attemptIngredientRemove(Entity e1, List<Entity> entities) {
        Bowl b = (Bowl) e1;
        entities.add(b.removeLastIngredient());
    }
}
