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

public class GameScreen implements Screen {

    private SuperNoodle game;
    private Level level;

    public GameScreen(SuperNoodle game) {
        this.game = game;
        Entity[] es = new Entity[1];
        es[0] = new Player(new Vector2(100, 100), 1, 1, new Texture(Gdx.files.internal("PLAYER.png")), 16, 0.99f);
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
