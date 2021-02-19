package com.patrick.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.patrick.game.screens.TitleScreen;

public class SuperNoodle extends Game {

    /*
   TODO: BUG -> If you jump with ingredient then drop, player starts flipping back and forth

   TODO: CHANGE -> Change physics to be more 'realistic'

   TODO: DESIGN -> Update look of HUD

   TODO: DESIGN -> Design start and win screens

   TODO: DESIGN -> Keep playing with particles
    */

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font; 

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new TitleScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setWindowedMode(2048, 1248);
//        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Gdx.graphics.setTitle(String.format("SUPER NOODLE", Gdx.graphics.getFramesPerSecond()));
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}