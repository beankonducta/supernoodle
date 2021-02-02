package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Settings;

import java.util.List;

public class MovementController {

    private CollisionController collisionController;

    public MovementController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public void playerMove(Entity e, List<Entity> entities, ShapeRenderer renderer) {
        if (Gdx.input.isKeyPressed(Input.Keys.D) && e.getGrounded()) {
            e.setVelocity(e.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && e.getGrounded()) {
            e.setVelocity(-e.getSpeed());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && e.getHeightGain() == 0 && e.getGrounded()) {
            e.setGrounded(false);
            e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) && e.getHeightGain() == 0 && e.getGrounded()) {
            e.setGrounded(false);
            e.setHeightGain(-Settings.PLAYER_FALL_MOD);
        }
        moveEntity(e, entities, renderer);
    }


    public void moveEntity(Entity e1, List<Entity> entities, ShapeRenderer renderer) {
        int weightMod = 2;
        Vector2 position = new Vector2(e1.getPosition().x + e1.getVelocity(), e1.getPosition().y + e1.getHeightGain());
        Vector2 offset = new Vector2(0, 0);
        Rectangle futurePosition = new Rectangle(position.x, position.y, e1.getCollider().width, e1.getCollider().height);
        if(Settings.DEBUG_COLLISION) {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.YELLOW);
            renderer.rect(futurePosition.x, futurePosition.y, futurePosition.width, futurePosition.height);
            renderer.end();
        }
        for(Entity e : entities) {
            if(e1 instanceof Player && e instanceof Floor) {
                if (collisionController.checkBasicCollision(futurePosition, e.getCollider())) {
                    offset = collisionController.calculateCollisionOffset(e1, e, position);
                    weightMod = 2;
                    e1.setGrounded(true);
                } else {
                    weightMod = 1;
                }
            }
        }
        e1.move(new Vector2(e1.getVelocity(), e1.getHeightGain() - (e1.getWeight() * weightMod) - offset.y));
    }
}
