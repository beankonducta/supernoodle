package com.patrick.game.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Ingredient;
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

    public boolean checkIngredientPickupCollision(Entity e1, Entity e2) {
        Ingredient i = (Ingredient) e2;
        if (i.getPickupCollider() != null) {
            return e1.getCollider().overlaps(i.getPickupCollider());
        }
        return false;
    }

    /*
    As it stands, if we enter through the bottom of a tile, we get 'pushed' to the top once we pass the half way point.

    I think we need to include a direction paramater here so we know the intended direction of travel (or maybe
    we can calculate this in the math) so if we're moving 'up' through a tile we don't get teleported through it.

    Also we aren't actually using the x value right now but we should get that working.
     */
    public Vector2 calculateFloorCollisionOffset(Entity e1, Entity e2, Vector2 position) {
        // this isn't actually calculating what we think it is :)
        // but it does seem to be working
        float x = 0;
        float y = 0;

        if (position.x > 0) x = e1.getPosition().x - (e2.getPosition().x + e2.getCollider().width);
        else if (position.x < 0) x = (e1.getPosition().x + e1.getCollider().width) - e2.getPosition().x;

        if (position.y < e1.getPosition().y) y = e1.getPosition().y - (e2.getPosition().y + e2.getCollider().height);
        else if (position.y > e1.getPosition().y) y = 0;

        return new Vector2(x, y);
    }
}