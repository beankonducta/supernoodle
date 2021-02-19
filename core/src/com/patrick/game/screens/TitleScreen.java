package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.SuperNoodle;
import com.patrick.game.controllers.*;
import com.patrick.game.entities.Cloud;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class TitleScreen implements Screen {

    private SuperNoodle game;
    private CameraController cameraController;
    private MovementController movementController;
    private ParticleController particleController;
    private CollisionController collisionController;
    private TitleScreenController titleScreenController;
    private MusicController musicController;

    private float deltaCounter;

    public TitleScreen(SuperNoodle game) {
        this.game = game;
        this.cameraController = new CameraController();
        this.particleController = new ParticleController();
        this.collisionController = new CollisionController();
        this.movementController = new MovementController(this.collisionController, this.cameraController, this.particleController);
        this.titleScreenController = new TitleScreenController(this.cameraController);
        this.musicController = new MusicController();
        this.deltaCounter = 0f;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameScreen(game));
                }
                if (keyCode == Input.Keys.NUM_1) {
                    cameraController.zoomOut(.1f);
                }
                if (keyCode == Input.Keys.NUM_0) {
                    cameraController.zoomIn(.1f);
                }
                if (keyCode == Settings.PLAYER_ONE_KEYS[4]) {
                    titleScreenController.setPlayerOneReady(true);
                }
                if (keyCode == Settings.PLAYER_TWO_KEYS[4]) {
                    titleScreenController.setPlayerTwoReady(true);
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        delta = java.lang.Math.min(1 / 30f, Gdx.graphics.getDeltaTime());
        this.deltaCounter += delta;
        if (this.deltaCounter >= 1) {
            this.incrementTimer();
            this.deltaCounter -= 1;
        }
        if (this.titleScreenController.getStartTimer() == 7) {
            game.setScreen(new GameScreen(game));
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.game.shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Settings.BLUE, Settings.BLUE, Settings.GREEN, Settings.GREEN);
        this.game.shapeRenderer.end();
        this.game.batch.begin();
        this.game.batch.setProjectionMatrix(this.cameraController.getCamera().combined);
        for (Cloud c : this.titleScreenController.getClouds()) {
            c.update(delta);
            this.movementController.cloudMove(c, delta);
            c.draw(this.game.batch);
        }
        this.game.batch.draw(Resources.LOGO, this.titleScreenController.getLogoPosition().x, this.titleScreenController.getLogoPosition().y);
        this.game.batch.draw(Resources.START_PLAQUE(1, this.titleScreenController.isPlayerOneReady()), this.titleScreenController.getPlayerOneStartPlaquePosition().x, this.titleScreenController.getPlayerOneStartPlaquePosition().y);
        this.game.batch.draw(Resources.START_PLAQUE(2, this.titleScreenController.isPlayerTwoReady()), this.titleScreenController.getPlayerTwoStartPlaquePosition().x, this.titleScreenController.getPlayerTwoStartPlaquePosition().y);
        this.titleScreenController.getPlayerOne().draw(this.game.batch);
        this.titleScreenController.getPlayerTwo().draw(this.game.batch);
        this.titleScreenController.getIngredientOne().draw(this.game.batch);
        this.titleScreenController.getIngredientTwo().draw(this.game.batch);
        if (this.titleScreenController.isPlayerOneReady() && this.titleScreenController.isPlayerTwoReady()) {
            this.game.batch.draw(Resources.COUNTDOWN(this.titleScreenController.getStartTimer()), this.cameraController.getCamera().viewportWidth / 2 - 16, this.cameraController.getCamera().viewportHeight / 2 - 16);
        }
        this.game.batch.end();
        this.titleScreenController.updateScreenPositions(delta);
    }

    private void incrementTimer() {
        if (this.titleScreenController.isPlayerTwoReady() && this.titleScreenController.isPlayerOneReady()) {
            if (this.titleScreenController.getStartTimer() < 7)
                this.titleScreenController.incrementStartTimer();
        }
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
