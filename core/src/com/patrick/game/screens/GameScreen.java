package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.*;
import com.patrick.game.entities.*;
import com.patrick.game.util.*;
import com.patrick.game.util.Math;

public class GameScreen implements Screen {

    private SuperNoodle game;
    private int winningBowl;
    private Map map;
    private MovementController movementController;
    private CollisionController collisionController;
    private CameraController cameraController;
    private LevelController levelController;
    private ParticleController particleController;
    private MusicController musicController;
    private MapLoader mapLoader;

    private SpriteBatch uiBatch;
    private SpriteBatch bgBatch;

    private boolean winCutscene;
    private float winCutsceneTime;

    public GameScreen(SuperNoodle game, MusicController musicController) {
        this.game = game;
        this.musicController = musicController;
        this.uiBatch = new SpriteBatch();
        this.bgBatch = new SpriteBatch();
        this.mapLoader = new MapLoader();
        this.map = mapLoader.loadMapToMap("MAP_0.png");
        this.particleController = new ParticleController();
        this.collisionController = new CollisionController();
        this.cameraController = new CameraController();
        this.movementController = new MovementController(collisionController, cameraController, particleController);
        this.levelController = new LevelController(collisionController, map);
        this.winCutscene = false;
        this.winningBowl = -1;
        this.winCutsceneTime = 0f;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        delta = java.lang.Math.min(1 / 30f, Gdx.graphics.getDeltaTime());
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.musicController.adjustMusic(this.map.bowlOne(), this.map.bowlTwo());
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.game.shapeRenderer.rect(0, 0, this.cameraController.getCamera().viewportWidth, this.cameraController.getCamera().viewportHeight, Settings.GREEN, Settings.GREEN, Settings.BLUE, Settings.BLUE);
        this.game.shapeRenderer.end();
        this.bgBatch.begin();
        this.bgBatch.setProjectionMatrix(this.cameraController.getCamera().combined);
        this.bgBatch.draw(Resources.MOUNTAINS, 0, 0);
        this.bgBatch.end();
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.cameraController.getCamera().combined);
        this.levelController.draw(this.game);
        this.levelController.update(delta);
        this.game.batch.end();
        this.uiBatch.begin();
        this.uiBatch.setProjectionMatrix(this.cameraController.getUiCamera().combined);
        if (Settings.SHOW_FPS)
            this.game.font.draw(this.uiBatch, Gdx.graphics.getFramesPerSecond() + "", 500, 500);
        this.uiBatch.draw(Resources.LOGO, this.cameraController.getUiCamera().viewportWidth / 2 - 112, this.cameraController.getUiCamera().viewportHeight - 140);
        for (Player p : this.map.getPlayers()) {
            this.movementController.playerMove(p, this.map, delta);
            if (this.winCutscene) {
                if (Misc.PLAYER_BOWL_MATCH_ID(p.getId(), this.winningBowl)) {
                    this.cameraController.moveCameraTowards(p, 225f, delta);
                    p.changeAnimation("DANCE", true);
                    p.setForcePlayAnimation(true);
                    this.particleController.randomWinParticlesAdd(this.map, p, 10);
                }
            } else if (p.getForcePlayAnimation()) p.setForcePlayAnimation(false);
        }
        for (Ingredient i : this.map.getIngredients())
            this.movementController.ingredientMove(i, this.map, delta);
        for (Bowl b : this.map.getBowls()) {
            if (b.getId() == -3) {
                this.uiBatch.draw(Resources.PLAQUE(1, this.levelController.getFillCount(b.getId())), 0, this.cameraController.getUiCamera().viewportHeight - Resources.PLAQUE_HEIGHT);
            } else {
                this.uiBatch.draw(Resources.PLAQUE(2, this.levelController.getFillCount(b.getId())), this.cameraController.getUiCamera().viewportWidth - Resources.PLAQUE_WIDTH, this.cameraController.getUiCamera().viewportHeight - Resources.PLAQUE_HEIGHT);
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
                        this.map = this.mapLoader.loadMapToMap("MAP_0.png");
                        this.levelController.setMap(this.map);
                        this.cameraController.resetCamera();
                        this.winCutscene = false;
                        this.winCutsceneTime = 0f;
                        this.winningBowl = -1;
                        this.movementController.start();
                    }
                }
            }
        }
        for (Cloud c : this.map.getClouds()) {
            this.movementController.cloudMove(c, delta);
        }
        for (Particle p : this.map.getParticles()) {
            this.movementController.particleMove(p, this.map, delta);
        }
        this.movementController.checkPlayerPlayerCollisions(this.map.playerOne(), this.map.playerTwo());
        this.uiBatch.end();
        this.movementController.updateEntityList(this.map);
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        if (Settings.DEBUG_COLLISION)
            this.game.shapeRenderer.circle(this.cameraController.getCamera().position.x, this.cameraController.getCamera().position.y, 5);
        for (Entity e : this.map.entities()) {
            if (Settings.DEBUG_COLLISION && e.getCollider() != null) {
                this.game.shapeRenderer.setColor((e.getDebugColor() != null ? e.getDebugColor() : Color.BLUE));
                this.game.shapeRenderer.rect(e.getCollider().x, e.getCollider().y, e.getCollider().width, e.getCollider().height);
            }
            if (Settings.DEBUG_COLLISION && e.getFloorCollider() != null) {
                this.game.shapeRenderer.setColor((e.getDebugColor() != null ? e.getDebugColor() : Color.BLUE));
                this.game.shapeRenderer.rect(e.getFloorCollider().x, e.getFloorCollider().y, e.getFloorCollider().width, e.getFloorCollider().height);
            }
            if (Settings.DEBUG_COLLISION && e instanceof Player) {
                Player p = (Player) e;
                this.game.shapeRenderer.setColor(Color.GREEN);
                this.game.shapeRenderer.rect(p.getBounceCollider().x, p.getBounceCollider().y, p.getBounceCollider().width, p.getBounceCollider().height);
            }
        }
        this.game.shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
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
