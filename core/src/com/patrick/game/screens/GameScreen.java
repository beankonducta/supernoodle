package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.controllers.CollisionController;
import com.patrick.game.controllers.LevelController;
import com.patrick.game.controllers.MovementController;
import com.patrick.game.entities.*;
import com.patrick.game.levels.Level;
import com.patrick.game.util.MapLoader;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private SuperNoodle game;
    private Level level;
    private int winningPlayer;
    private List<Entity> entities;
    private MovementController movementController;
    private CollisionController collisionController;
    private CameraController cameraController;
    private LevelController levelController;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        MapLoader mapLoader = new MapLoader();
        collisionController = new CollisionController();
        cameraController = new CameraController();
        movementController = new MovementController(collisionController, cameraController);
        levelController = new LevelController(collisionController);
        entities = mapLoader.loadMap("MAP_0.png");
        this.level = new Level(entities);
    }

    @Override
    public void show() {
    }

    Color blue = new Color(0, .6f, .9f, 1);
    Color green = new Color(0, .6f, .5f, 1);

    @Override
    public void render(float delta) {
        if (level == null) return;
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.game.shapeRenderer.rect(0, 0, this.cameraController.getCamera().viewportWidth, this.cameraController.getCamera().viewportHeight, green, green, blue, blue);
        this.game.shapeRenderer.end();
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.cameraController.getCamera().combined);
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
            if(e instanceof Cloud)
                movementController.cloudMove(e, delta);
        }
        movementController.updateEntityList(entities);
        this.game.batch.end();
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Entity e : entities) {
            if (Settings.DEBUG_COLLISION && e.getCollider() != null) {
                this.game.shapeRenderer.setColor((e.getDebugColor() != null ? e.getDebugColor() : Color.BLUE));
                this.game.shapeRenderer.rect(e.getCollider().x, e.getCollider().y, e.getCollider().width, e.getCollider().height);
            }
            if (Settings.DEBUG_COLLISION && e.getFloorCollider() != null) {
                this.game.shapeRenderer.setColor((e.getDebugColor() != null ? e.getDebugColor() : Color.BLUE));
                this.game.shapeRenderer.rect(e.getFloorCollider().x, e.getFloorCollider().y, e.getFloorCollider().width, e.getFloorCollider().height);
            }
        }
        this.game.shapeRenderer.end();
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
