package com.patrick.game.controllers;

import com.badlogic.gdx.audio.Music;
import com.patrick.game.entities.Bowl;
import com.patrick.game.util.Resources;

public class MusicController {

    private Music music;
    private int musicId;

    public MusicController() {
        this.setMusic(0);
    }

    /**
     * Sets the current music, and starts it at the position of the previous music (if applicable).
     *
     * @param id
     */
    public void setMusic(int id) {
        if(id > Resources.MUSIC.length - 1) return;
        final float pos = this.music == null? 0 : this.music.getPosition();
        this.musicId = id;
        this.stop();
        this.music = Resources.MUSIC[id];
        this.music.setPosition(pos);
        this.music.setLooping(true);
        this.music.setVolume(.1f);
        this.play();
    }

    /**
     * Changes the music based on the bowl with the most ingredients.
     *
     * @param b1
     * @param b2
     */
    public void adjustMusic(Bowl b1, Bowl b2) {
        if(b1 == null || b2 == null) return;
        final int count = b1.getIngredientCount() > b2.getIngredientCount() ? b1.getIngredientCount() + 1: b2.getIngredientCount() + 1;
        if(count != this.musicId) this.setMusic(count);
    }

    /**
     * Stops the music.
     *
     */
    public void stop() {
        if(this.music == null) return;
        this.music.stop();
    }

    /**
     * Plays the music.
     *
     */
    public void play() {
        if(this.music == null) return;
        this.music.play();
    }

    /**
     * Pauses the music.
     *
     */
    public void pause() {
        if(this.music == null) return;
        this.music.pause();
    }

    /**
     * Disposes the music.
     *
     */
    public void dispose() {
        if(this.music == null) return;
        this.music.dispose();
    }
}
