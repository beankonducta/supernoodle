package com.patrick.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.controllers.MusicController;
import com.patrick.game.controllers.SoundController;
import com.patrick.game.screens.TitleScreen;

public class SuperNoodle extends Game {

    /*
   TODO: BUG -> If you jump with ingredient then drop, player starts flipping back and forth (sometimes?)

   TODO: CHANGE -> Change physics to be more 'realistic'

   TODO: DESIGN -> Keep playing with particles

   TODO: REFACTOR -> Consider more singleton controllers

   TODO: REFACTOR -> Consider moving singletons and controllers to different packages (and renaming one or the other)
    */

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font; 

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        setScreen(new TitleScreen(this, 0, 0, 0));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setVSync(true);
//        Gdx.graphics.setWindowedMode(2048, 1248);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Gdx.graphics.setTitle(String.format("SUPER NOODLE", Gdx.graphics.getFramesPerSecond()));
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        SoundController.disposeAllSounds();
        MusicController.dispose();
    }
}