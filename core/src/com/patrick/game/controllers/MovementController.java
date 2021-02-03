package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Ingredient;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Settings;

import java.util.List;

public class MovementController {

    private CollisionController collisionController;

    public MovementController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public void playerMove(Entity e, List<Entity> entities, ShapeRenderer renderer) {
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
                                attemptPickup(e, e1);
                    }
            }
        }
        moveEntity(e, entities, renderer);
    }

    public void attemptPickup(Entity e1, Entity e2) {
        if (e1 instanceof Player && e2 instanceof Ingredient) {
            Player p = (Player) e1;
            if (p.getIngredient() == null) {
                Ingredient i = (Ingredient) e2;
                i.setOffset(new Vector2(-8, 8));
                p.setIngredient(i);
            }
        }
    }

    public void ingredientMove(Entity e, List<Entity> entities, ShapeRenderer renderer) {
        if (e instanceof Ingredient) {
            moveEntity(e, entities, renderer);
        }
    }

    public void moveEntity(Entity e1, List<Entity> entities, ShapeRenderer renderer) {
        int weightMod = 2;
        float veloMod = e1.getGrounded() ? 1 : .5f;
        float xOffset = 0;
        float yOffset = 0;
        Vector2 position = new Vector2(e1.getPosition().x + (e1.getVelocity() * veloMod), e1.getPosition().y - (e1.getWeight()) + e1.getHeightGain());
        Vector2 offset = new Vector2(0, 0);
        Rectangle futurePosition = new Rectangle(position.x, position.y, e1.getCollider().width, e1.getCollider().height);
        if (Settings.DEBUG_COLLISION) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.YELLOW);
            renderer.rect(futurePosition.x, futurePosition.y, futurePosition.width, futurePosition.height);
            renderer.end();
        }
        for (Entity e : entities) {
            if (e instanceof Floor) {
                if (collisionController.checkBasicCollision(futurePosition, e.getCollider())) {
                    offset = collisionController.calculateFloorCollisionOffset(e1, e, position);
                    weightMod = 1;
                    e1.setGrounded(true);
                } else {
                    weightMod = 2;
                }
            }
            if (e1.getId() != e.getId() && !(e instanceof Floor)) {
                if (collisionController.checkBasicCollision(e1, e)) {
                    xOffset = collisionController.calculateDoubleCollisionVelocityOffset(e1, e);
                    if (e1 instanceof Ingredient)
                        e1.setVelocity(xOffset);
                }
            }
        }
        e1.move(new Vector2((e1.getVelocity() * veloMod) + xOffset, e1.getHeightGain() - (e1.getWeight() * weightMod) - offset.y + yOffset));
    }
}
