package com.patrick.game.controllers;

import com.patrick.game.entities.Bowl;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Ingredient;

import java.util.List;

public class LevelController {

    private CollisionController collisionController;

    public LevelController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    private void update(float delta) {

    }
}