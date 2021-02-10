package com.patrick.game.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Ingredient;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Settings;

public class CollisionController {

    public CollisionController() {

    }

    public boolean checkBasicCollision(Entity e1, Entity e2) {
        if (e1.getCollider().overlaps(e2.getCollider()))
            return true;
        return false;
    }

    public boolean checkBasicFloorCollision(Entity e1, Entity e2) {
        if(e1.getFloorCollider() != null && e1.getPosition().y <= Settings.TILE_SIZE * 3)
            if (e1.getFloorCollider().overlaps(e2.getCollider()))
                return true;
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

    // this is redundant, since we can just use the simple collisions above
    public boolean checkIngredientPickupCollision(Entity e1, Entity e2) {
        Ingredient i = (Ingredient) e2;
        if (i.getPickupCollider() != null) {
            return e1.getCollider().overlaps(i.getPickupCollider());
        }
        return false;
    }

//    public Vector2 calculateFloorCollisionOffset(Entity e1, Entity e2, Vector2 position) {
//        // the player keeps falling through the corners haha
//        float x = 0;
//        float y = 0;
//        if (position.y > e2.getPosition().y) y = e1.getPosition().y - (e2.getPosition().y + e1.getCollider().height);
//        else if (position.y < e2.getPosition().y) y = 0;
//        if (position.x > e2.getPosition().x) x = e1.getPosition().x - (e2.getPosition().x + e2.getCollider().width);
//        else if (position.x < e2.getPosition().x) x = (e1.getPosition().x + e1.getCollider().width) - e2.getPosition().x;
//        if(x < y) y = 0;
//        if(y < x) x = 0;
//        return new Vector2(x, y);
//    }

    public Vector2 calculateFloorCollisionOffset(Entity e1, Entity e2) {
        // the player keeps falling through the corners haha
        float x = 0;
        float y = 0;
        if(e1.getPosition().y > e2.getPosition().y && e1.getHeightGain() <= e1.getWeight()) {
            y = -1*(e1.getPosition().y - e1.getCollider().height - e2.getPosition().y + 1);
        }
        else if(e1.getPosition().y < e2.getPosition().y && e1.getHeightGain() > 0) {
            y = 0;
        }
        else if(e1.getPosition().x > e2.getPosition().x && e1.getVelocity() < 0 && e1.getDir() == Direction.LEFT) {
            x = -1*(e1.getPosition().x - e1.getCollider().width - e2.getPosition().x + 1);
        }
        else if(e1.getPosition().x < e2.getPosition().x && e1.getVelocity() > 0 && e1.getDir() == Direction.RIGHT) {
            x = e2.getPosition().x - e1.getCollider().width - e1.getPosition().x + 1;
        }
        return new Vector2(x, y);
    }
}