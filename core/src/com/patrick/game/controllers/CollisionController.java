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
        if(e1.getFloorCollider() != null && e1.y() <= Settings.TILE_SIZE * 3)
            if (e1.getFloorCollider().overlaps(e2.getCollider()))
                return true;
        if (e1.getCollider().overlaps(e2.getCollider()))
            return true;
        return false;
    }

    public boolean checkPlayerBowlCollision(Entity e1, Entity e2) {
        if(e1.getFloorCollider() != null)
            if (e1.getFloorCollider().overlaps(e2.getCollider()))
                return true;
        if (e1.getCollider().overlaps(e2.getCollider()))
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

    public boolean checkPlayerHeadBounceCollision(Player p1, Player p2) {
        return p1.getBounceCollider().overlaps(p2.getBounceCollider());
    }

    public Vector2 calculateFloorCollisionOffset(Entity e1, Entity e2) {
        float x = 0;
        float y = 0;
        if(e1.y() > e2.y() && e1.getHeightGain() <= e1.getWeight()) {
            y = -1*(e1.y() - e1.height() - e2.y() + 1);
        }
        else if(e1.y() < e2.y() && e1.getHeightGain() > 0) {
            y = 0;
        }
        else if(e1.x() > e2.x() && e1.getVelocity() < 0 && e1.getDir() == Direction.LEFT) {
            x = -1*(e1.x() - e1.width() - e2.x() + 1);
        }
        else if(e1.x() < e2.x() && e1.getVelocity() > 0 && e1.getDir() == Direction.RIGHT) {
            x = e2.x() - e1.width() - e1.x() + 1;
        }
        return new Vector2(x, y);
    }
}