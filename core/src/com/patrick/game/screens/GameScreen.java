package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.controllers.CollisionController;
import com.patrick.game.controllers.LevelController;
import com.patrick.game.controllers.MovementController;
import com.patrick.game.entities.*;
import com.patrick.game.levels.Level;
import com.patrick.game.util.MapLoader;
import com.patrick.game.util.Settings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private SuperNoodle game;
    private Level level;
    private int winningPlayer;
    private List<Entity> entities = new ArrayList<Entity>();
    private MovementController movementController;
    private CollisionController collisionController;
    private CameraController cameraController;
    private LevelController levelController;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        MapLoader mapLoader = new MapLoader();
        entities = mapLoader.loadMap("map.png");
        collisionController = new CollisionController();
        movementController = new MovementController(collisionController);
        levelController = new LevelController(collisionController);
        cameraController = new CameraController();
        this.level = new Level(entities);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (level == null) return;
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.cameraController.getCamera().projection);
        this.level.draw(this.game.batch, this.game.shapeRenderer);
        this.level.update(delta);
        if(entities != null)
        for(Entity e : entities) {
            if(e instanceof Player)
                movementController.playerMove(e, entities, game.shapeRenderer, delta);
            if(e instanceof Ingredient)
                movementController.ingredientMove(e, entities, game.shapeRenderer, delta);
            if(e instanceof Bowl) {
                Bowl b = (Bowl)e;
                winningPlayer = levelController.checkWin(b);
                if(winningPlayer != -1)
                    game.setScreen(new WinScreen(game, winningPlayer));
            }
        }
        movementController.updateEntityList(entities);
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
