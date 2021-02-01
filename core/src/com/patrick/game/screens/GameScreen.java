package com.patrick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.SuperNoodle;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Player;
import com.patrick.game.levels.Level;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private SuperNoodle game;
    private Level level;
    private Player playerOne;
    private Player playerTwo;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        List<Entity> es = new ArrayList<Entity>();
        playerOne = new Player(new Vector2(100, 100), 1, 1, 1, .1f, new Texture(Gdx.files.internal("PLAYER.png")), 16, 0.99f);
        playerTwo = new Player(new Vector2(500, 400), 1, 1, 1, .1f, new Texture(Gdx.files.internal("PLAYER.png")), 16, 0.99f);
        es.add(playerOne);
        es.add(playerTwo);
        this.level = new Level(es);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
          // process inputs
        });
    }

    @Override
    public void render(float delta) {
       if(level == null) return;
       this.game.batch.begin();
       this.level.draw(this.game.batch);
       this.level.update();
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
