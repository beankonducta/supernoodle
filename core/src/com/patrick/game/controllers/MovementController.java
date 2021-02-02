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
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            e.setVelocity(e.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            e.setVelocity(-e.getSpeed());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && e.getHeightGain() == 0) {
            e.setHeightGain(Settings.PLAYER_JUMP_HEIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S) && e.getHeightGain() == 0) {
            e.setHeightGain(-Settings.PLAYER_FALL_MOD);
        }
        moveEntity(e, entities, renderer);
    }


    public void moveEntity(Entity e1, List<Entity> entities, ShapeRenderer renderer) {
        Vector2 position = new Vector2(e1.getPosition().x + e1.getVelocity(), e1.getPosition().y - e1.getWeight() + e1.getHeightGain());
        Vector2 offset = new Vector2(0, 0);
        Rectangle temp = new Rectangle(position.x, position.y, e1.getCollider().width, e1.getCollider().height);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.YELLOW);
        renderer.rect(temp.x, temp.y, temp.width, temp.height);
        renderer.end();
        for(Entity e : entities) {
            if(e1 instanceof Player && e instanceof Floor) {
                if(collisionController.checkBasicCollision(temp, e.getCollider())) {
                   offset = collisionController.calculateCollisionOffset(e1, e, position);
                   e1.setVelocity(0);
                   break;
                }
            }
        }
        System.out.println(offset);
        e1.move(new Vector2(e1.getVelocity(), -e1.getWeight() + e1.getHeightGain() - offset.y));
        // check if temp collides with any floors
        // if it does, calculate the distance between player and floor and force him to move only that far
        // if it doesn't, make the move!
    }
}
