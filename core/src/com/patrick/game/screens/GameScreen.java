package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.SuperNoodle;

public class GameScreen implements Screen {

    private SuperNoodle game;

    public GameScreen(SuperNoodle game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
          // process inputs
        });
    }

    @Override
    public void render(float delta) {
       // render the game
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
