package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.CollisionController;
import com.patrick.game.controllers.MovementController;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Floor;
import com.patrick.game.entities.Ingredient;
import com.patrick.game.entities.Player;
import com.patrick.game.levels.Level;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private SuperNoodle game;
    private Level level;
    private Player playerOne;
    private Player playerTwo;
    private Ingredient ingredientOne;
    private Ingredient ingredientTwo;
    private List<Entity> entities = new ArrayList<Entity>();
    private MovementController movementController;
    private CollisionController collisionController;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        entities.add(new Floor(new Vector2(100, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(150, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(200, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(250, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(300, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(350, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(400, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(450, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(500, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        entities.add(new Floor(new Vector2(550, 120), new Texture(Gdx.files.internal("PLAYER.png"))));
        ingredientOne = new Ingredient(new Vector2(200, 400), Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED, new Texture(Gdx.files.internal("INGREDIENT.png")), 3);
        ingredientTwo = new Ingredient(new Vector2(300, 200), Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED, new Texture(Gdx.files.internal("INGREDIENT.png")), 4);
        entities.add(ingredientOne);
        entities.add(ingredientTwo);
        playerOne = new Player(new Vector2(100, 400), Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED, new Texture(Gdx.files.internal("PLAYER.png")), 16, 0.99f, 1);
        playerTwo = new Player(new Vector2(500, 400), Settings.PLAYER_SPEED, Settings.PLAYER_WEIGHT, Settings.PLAYER_DECEL_SPEED, new Texture(Gdx.files.internal("PLAYER.png")), 16, 0.99f, 2);
        entities.add(playerOne);
        entities.add(playerTwo);
        collisionController = new CollisionController();
        movementController = new MovementController(collisionController);
        this.level = new Level(entities);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (level == null) return;
        this.game.batch.begin();
        this.level.draw(this.game.batch, this.game.shapeRenderer);
        this.level.update(delta);
        movementController.playerMove(playerOne, entities, game.shapeRenderer);
        movementController.playerMove(playerTwo, entities, game.shapeRenderer);
        movementController.ingredientMove(ingredientOne, entities, game.shapeRenderer);
        movementController.ingredientMove(ingredientTwo, entities, game.shapeRenderer);
        this.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
