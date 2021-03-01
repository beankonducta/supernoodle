package com.patrick.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;
import com.patrick.game.util.Settings;

public class CollisionController {

    public CollisionController() {
    }

    /**
     * Returns whether or not entity 1 and entity 2 have collided (based on simple rectangle collision).
     *
     * @param e1
     * @param e2
     * @return
     */
    public boolean checkBasicCollision(Entity e1, Entity e2) {
        if (e1.getCollider().overlaps(e2.getCollider()))
            return true;
        return false;
    }

    /**
     * Returns whether or not we have a floor specific collision. Used in particular to check collision with the
     * entity's floor collider, which is a larger rectangle to prevent the entity from falling through the ground.
     *
     * @param e1
     * @param e2
     * @param viewWidth
     * @return
     */
    public boolean checkBasicFloorCollision(Entity e1, Entity e2, float viewWidth) {
        if(e1.getFloorCollider() != null && (e1.y() <= Settings.TILE_SIZE * 3) || e1.x() < Settings.TILE_SIZE || e1.x() > viewWidth)
            if (e1.getFloorCollider().overlaps(e2.getCollider()))
                return true;
        if (e1.getCollider().overlaps(e2.getCollider()))
            return true;
        return false;
    }

    /**
     * Returns whether or not an entity has collided with a bowl.
     *
     * @param p
     * @param b
     * @return
     */
    public boolean checkPlayerBowlCollision(Player p, Bowl b) {
        if(p.getFloorCollider() != null)
            if (p.getFloorCollider().overlaps(b.getCollider()))
                return true;
        if (p.getCollider().overlaps(b.getCollider()))
            return true;
        return false;
    }

    /**
     * Returns whether or not an entity has collided with an ingredient's (entity 2) pickup collider.
     *
     * @param e
     * @param i
     * @return
     */
    public boolean checkIngredientPickupCollision(Entity e, Ingredient i) {
        if (i.getPickupCollider() != null) {
            return e.getCollider().overlaps(i.getPickupCollider());
        }
        return false;
    }

    /**
     * Returns whether or not a bowl's ingredient collider overlaps the ingredient.
     *
     * @param b
     * @param i
     * @return
     */
    public boolean checkIngredientBowlNearby(Bowl b, Ingredient i) {
        return b.getIngredientCollider().overlaps(i.getCollider());
    }

    /**
     * Returns whether or not a player has collided with another player's bounce collider.
     *
     * @param p1
     * @param p2
     * @return
     */
    public boolean checkPlayerHeadBounceCollision(Player p1, Player p2) {
        return p1.getBounceCollider().overlaps(p2.getBounceCollider());
    }

    /**
     * Returns an offset calculated by the distance between entity one and the floor (entity two).
     * Used to prevent entity from falling through the floor and getting stuck in it when calculating collision,
     * rather the offset is applied before the move to ensure the entity always sits on top of the floor.
     *
     * @param e1
     * @param e2
     * @return
     */
    public Vector2 calculateFloorCollisionOffset(Entity e1, Entity e2) {
        float x = 0;
        float y = 0;
        if(e1.y() > e2.y() && e1.getHeightGain() <= e1.getWeight()) {
            y = -1*(e1.y() - e1.height() - e2.y() + 1);
        }
        else if(e1.y() < e2.y() && e1.getHeightGain() > 0) {
            y = 0;
        }
//        unused x collision here
//        else if(e1.x() > e2.x() && e1.getVelocity() < 0 && e1.getDir() == Direction.LEFT) {
//            x = -1*(e1.x() - e1.width() - e2.x() + 1);
//        }
//        else if(e1.x() < e2.x() && e1.getVelocity() > 0 && e1.getDir() == Direction.RIGHT) {
//            x = e2.x() - e1.width() - e1.x() + 1;
//        }
        return new Vector2(x, y);
    }
}