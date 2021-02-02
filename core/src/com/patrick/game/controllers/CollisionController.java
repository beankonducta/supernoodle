package com.patrick.game.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Player;

public class CollisionController {

    public CollisionController() {

    }

    public boolean checkBasicCollision(Entity e1, Entity e2) {
        if (e1.getCollider().overlaps(e2.getCollider()))
            return true;
        return false;
    }

    public boolean checkBasicCollision(Rectangle r1, Rectangle r2) {
        if (r1.overlaps(r2))
            return true;
        return false;
    }

    public boolean checkPlayerFloorCollision(Entity e1, Entity e2) {
        if (e1.getCollider().overlaps(e2.getCollider()) && (e1 instanceof Player && e2 instanceof Floor) || (e1 instanceof Floor && e2 instanceof Player))
            return true;
        return false;
    }

    public Vector2 calculateCollisionOffset(Entity e2, Entity e1, Vector2 position) {
        float x = 0;
        if (position.x > 0) x = e2.getPosition().x - (e1.getPosition().x + e1.getCollider().width);
        else if (position.x < 0) x = (e2.getPosition().x + e2.getCollider().width) - e1.getPosition().x;
        float y = 0;
        if (position.y > 0) y = e2.getPosition().y - (e1.getPosition().y + e1.getCollider().height);
        else if (position.y < 0) y = (e2.getPosition().y + e2.getCollider().height) - e1.getPosition().y;
        return new Vector2(x, y);
    }
}