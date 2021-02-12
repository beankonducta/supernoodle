package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.CameraController;
import com.patrick.game.controllers.CollisionController;
import com.patrick.game.controllers.LevelController;
import com.patrick.game.controllers.MovementController;
import com.patrick.game.entities.*;
import com.patrick.game.levels.Level;
import com.patrick.game.util.*;
import jdk.internal.loader.Resource;

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

    private SpriteBatch uiBatch;

    private boolean winCutscene;
    private float winCutsceneTime;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        this.uiBatch = new SpriteBatch();
        this.mapLoader = new MapLoader();
        this.collisionController = new CollisionController();
        this.cameraController = new CameraController();
        this.movementController = new MovementController(collisionController, cameraController);
        this.levelController = new LevelController(collisionController);
        this.entities = mapLoader.loadMap("MAP_0.png");
        this.level = new Level(entities);
        this.winCutscene = false;
        this.winningBowl = -1;
        this.winCutsceneTime = 0f;
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
        this.level.draw(this.game);
        this.level.update(delta);
        this.game.batch.end();
        this.uiBatch.begin();
        this.uiBatch.setProjectionMatrix(this.cameraController.getUiCamera().combined);
        if (this.entities != null)
            for (Entity e : this.entities) {
                if (e instanceof Player) {
                    this.movementController.playerMove(e, this.entities, delta);
                    Player p = (Player) e;
                    if (this.winCutscene) {
                        if (Misc.PLAYER_BOWL_MATCH_ID(e.getId(), this.winningBowl)) {
                            this.cameraController.moveCameraTowards(e, 1f, delta);
                            p.changeAnimation("DANCE", true);
                            p.setForcePlayAnimation(true);
                        }
                    }
                    else if(p.getForcePlayAnimation()) p.setForcePlayAnimation(false);
                }
                if (e instanceof Ingredient)
                    this.movementController.ingredientMove(e, this.entities, delta);
                if (e instanceof Bowl) {
                    Bowl b = (Bowl) e;
                    // these are very janky and hardcoded, need to figure out a better way to update them (maybe merge the two sprites?)
                if(b.getId() == -3) {
                    this.uiBatch.draw(Resources.PLAQUE(1, this.levelController.getFillCount(b.getId())), 0 + (Resources.PLAQUE_WIDTH * .05f), this.cameraController.getUiCamera().viewportHeight - (Resources.PLAQUE_HEIGHT * 1.22f));
                }
                else {
                    this.uiBatch.draw(Resources.PLAQUE(2, this.levelController.getFillCount(b.getId())), this.cameraController.getUiCamera().viewportWidth - (Resources.PLAQUE_WIDTH * 1.05f), this.cameraController.getUiCamera().viewportHeight - (Resources.PLAQUE_HEIGHT * 1.22f));
                }
                    if (this.levelController.checkFull(b) || this.winCutscene) {
                        if (this.winningBowl == b.getId() || this.winningBowl == -1) {
                            this.movementController.stop();
                            if (this.winCutsceneTime == 0)
                                this.levelController.increaseFillCount(b);
                            this.winCutscene = true;
                            this.winCutsceneTime += delta;
                            this.winningBowl = b.getId();
                            if (this.winCutsceneTime >= Settings.DANCE_TIME) {
                                if (this.levelController.checkWin(b)) {
                                    this.cameraController.resetCamera();
                                    this.game.setScreen(new WinScreen(this.game, this.winningBowl));
                                }
                                this.entities = this.mapLoader.loadMap("MAP_0.png");
                                this.level = new Level(this.entities);
                                this.cameraController.resetCamera();
                                this.winCutscene = false;
                                this.winCutsceneTime = 0f;
                                this.winningBowl = -1;
                                this.movementController.start();
                            }
                        }
                    }
                }
                if (e instanceof Cloud)
                    this.movementController.cloudMove(e, delta);
            }
        this.uiBatch.end();
        this.movementController.updateEntityList(this.entities);
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
        this.cameraController.resetCamera();
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
