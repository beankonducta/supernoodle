package com.patrick.game.controllers;

import com.badlogic.gdx.audio.Music;
import com.patrick.game.entities.Bowl;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class MusicController {

    private static Music music;
    private static int musicId;

    /**
     * Sets the current music, and starts it at the position of the previous music (if applicable).
     *
     * @param id
     */
    public static void setMusic(int id) {
        if(id > Resources.MUSIC.length - 1) return;
        final float pos = music == null? 0 : music.getPosition();
        musicId = id;
        stop();
        music = Resources.MUSIC[id];
        music.setPosition(pos);
        music.setLooping(true);
        music.setVolume(Settings.MUSIC_VOLUME);
        play();
    }

    /**
     * Changes the music based on the bowl with the most ingredients.
     *
     * @param b1
     * @param b2
     */
    public static void adjustMusic(Bowl b1, Bowl b2) {
        if(b1 == null || b2 == null) return;
        final int count = b1.getIngredientCount() > b2.getIngredientCount() ? b1.getIngredientCount() + 1: b2.getIngredientCount() + 1;
        if(count != musicId) setMusic(count);
    }

    /**
     * Stops the music.
     *
     */
    public static void stop() {
        if(music == null) return;
        music.stop();
    }

    /**
     * Plays the music.
     *
     */
    public static void play() {
        if(music == null) return;
        music.play();
    }

    /**
     * Pauses the music.
     *
     */
    public static void pause() {
        if(music == null) return;
        music.pause();
    }

    /**
     * Disposes the music.
     *
     */
    public static void dispose() {
        if(music == null) return;
        music.dispose();
    }
}
