package com.patrick.game.controllers;

import com.patrick.game.entities.Bowl;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Ingredient;
import com.patrick.game.entities.Player;

import java.util.List;

public class LevelController {

    private CollisionController collisionController;

    public LevelController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    private void update(float delta) {

    }

    public int checkWin(Bowl b) {
        if(b.getIngredientCount() == 5) {
            return b.getId();
        }
        return -1;
    }
}