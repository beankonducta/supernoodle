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

    public Vector2 calculateCollisionOffset(Entity e1, Entity e2) {
        // i think if we can calculate the 'space to collision' before moving and return it we can just subtract it from the move.
        return new Vector2(0, 0);
    }
}
