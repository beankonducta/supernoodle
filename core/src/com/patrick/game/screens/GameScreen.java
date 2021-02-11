package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.controllers.CollisionController;
import com.patrick.game.controllers.LevelController;
import com.patrick.game.controllers.MovementController;
import com.patrick.game.entities.*;
import com.patrick.game.levels.Level;
import com.patrick.game.util.*;
import com.patrick.game.util.Math;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private SuperNoodle game;
    private Level level;
    private int winningBowl;
    private List<Entity> entities;
    private MovementController movementController;
    private CollisionController collisionController;
    private CameraController cameraController;
    private LevelController levelController;
    private MapLoader mapLoader;

    private boolean winCutscene;
    private float winCutsceneTime;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        mapLoader = new MapLoader();
        collisionController = new CollisionController();
        cameraController = new CameraController();
        movementController = new MovementController(collisionController, cameraController);
        levelController = new LevelController(collisionController);
        entities = mapLoader.loadMap("MAP_0.png");
        this.level = new Level(entities);
        winCutscene = false;
        winningBowl = -1;
        winCutsceneTime = 0f;
    }

    @Override
    public void show() {
    }

    Color blue = new Color(0, .6f, .9f, 1);
    Color green = new Color(0, .6f, .5f, 1);

    @Override
    public void render(float delta) {
        delta = java.lang.Math.min(1/30f, Gdx.graphics.getDeltaTime());
        if (level == null) return;
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.game.shapeRenderer.rect(0, 0, this.cameraController.getCamera().viewportWidth, this.cameraController.getCamera().viewportHeight, green, green, blue, blue);
        this.game.shapeRenderer.end();
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.cameraController.getCamera().combined);
        if (entities != null)
            for (Entity e : entities) {
                if (e instanceof Player) {
                    movementController.playerMove(e, entities, game.shapeRenderer, delta);
                    if (winCutscene) {
                        if (Misc.PLAYER_BOWL_MATCH_ID(e.getId(), winningBowl)) {
                            Player p = (Player) e;
                            p.changeAnimation("DANCE", true);
                            float speed = p.getDir() == Direction.LEFT ? -Settings.PLAYER_SPEED : Settings.PLAYER_SPEED;
                            if (p.getVelocity() != speed)
                                p.setVelocity(speed);
                        }
                    }
                }
                if (e instanceof Ingredient)
                    movementController.ingredientMove(e, entities, game.shapeRenderer, delta);
                if (e instanceof Bowl) {
                    Bowl b = (Bowl) e;
                    // these are very janky and hardcoded, need to figure out a better way to update them (maybe merge the two sprites?)
                if(b.getId() == -3) {
                    this.game.batch.draw(Resources.PLAYER_1_BANNER, 0, cameraController.getCamera().viewportHeight - Resources.PLAYER_1_BANNER.getHeight() * 1.5f);
                    this.game.batch.draw(Resources.BOWL_COUNT(levelController.getFillCount(b.getId())), Settings.TILE_SIZE +4, cameraController.getCamera().viewportHeight - Resources.PLAYER_1_BANNER.getHeight() * 1.32f);
                }
                else {
                    this.game.batch.draw(Resources.PLAYER_2_BANNER, cameraController.getCamera().viewportWidth - Resources.PLAYER_2_BANNER.getWidth(), cameraController.getCamera().viewportHeight - Resources.PLAYER_1_BANNER.getHeight() * 1.5f);
                    this.game.batch.draw(Resources.BOWL_COUNT(levelController.getFillCount(b.getId())), cameraController.getCamera().viewportWidth - (Resources.PLAYER_2_BANNER.getWidth() / 2) - (Settings.TILE_SIZE * 1.6f), cameraController.getCamera().viewportHeight - Resources.PLAYER_1_BANNER.getHeight() * 1.32f);
                }
                    if (levelController.checkFull(b) || winCutscene) {
                        if (winningBowl == b.getId() || winningBowl == -1) {
                            movementController.stop();
                            if (winCutsceneTime == 0)
                                levelController.increaseFillCount(b);
                            winCutscene = true;
                            winCutsceneTime += delta;
                            winningBowl = b.getId();
                            if (winCutsceneTime >= Settings.DANCE_TIME) {
                                if (levelController.checkWin(b)) {
                                    game.setScreen(new WinScreen(game, winningBowl));
                                }
                                entities = mapLoader.loadMap("MAP_0.png");
                                this.level = new Level(entities);
                                winCutscene = false;
                                winCutsceneTime = 0f;
                                winningBowl = -1;
                                movementController.start();
                            }
                        }
                    }
                }
                if (e instanceof Cloud)
                    movementController.cloudMove(e, delta);
            }
        movementController.updateEntityList(entities);
        this.level.draw(this.game);
        this.level.update(delta);
        this.game.batch.end();
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity e : entities) {
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
        this.pause();
        this.cameraController.resetCamera();
        this.resume();
    }

    @Override
    public void pause() {
        this.movementController.pause();
    }

    @Override
    public void resume() {
        this.movementController.start();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
