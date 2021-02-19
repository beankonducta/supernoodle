package com.patrick.game.controllers;

import com.badlogic.gdx.audio.Music;
import com.patrick.game.util.Resources;

public class MusicController {

    private Music music;

    public MusicController() {
        this.music = Resources.DEFAULT_MUSIC;
        this.music.play();
    }

    public void play() {
        this.music.play();
    }

    public void pause() {
        this.music.pause();
    }

    public void setMusic(Music music) {
        this.dispose();
        this.music = music;
    }

    public Music getMusic() {
        return this.music;
    }

    public void stop() {
        this.music.stop();
    }

    public void dispose() {
        this.music.dispose();
    }
}
