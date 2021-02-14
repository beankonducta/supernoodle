package com.patrick.game.controllers;

import com.patrick.game.entities.Bowl;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Ingredient;
import com.patrick.game.entities.Player;

import java.util.List;

public class LevelController {

    private CollisionController collisionController;
    private int fillCount1;
    private int fillCount2;

    public int getFillCount(int id) {
        return id == -3 ? fillCount1 : fillCount2;
    }

    public LevelController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    private void update(float delta) {}

    public boolean checkFull(Bowl b) {
        return b.getIngredientCount() == 5;
    }

    public boolean checkWin(Bowl b) {
        return b.getId() == -3 ? this.fillCount1 == 3 : this.fillCount2 == 3;
    }

    public void increaseFillCount(Bowl b) {
        if (b.getId() == -3) this.fillCount1++;
        else this.fillCount2++;
        b.removeLastIngredient();
    }

}