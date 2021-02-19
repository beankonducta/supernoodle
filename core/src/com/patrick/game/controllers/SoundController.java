package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundController {

    private List<Sound> sounds;

    public SoundController() {
        this.sounds = new ArrayList<>();
    }

    public void playSound(Sound sound) {
        if(!this.sounds.contains(sound)) this.sounds.add(sound);
        sound.play();
    }

    public void playSoundAtVolue(Sound sound, float volume) {
        sound.play(volume);
    }

    public void stopSound(Sound sound) {
        sound.stop();
    }

    public void disposeSound(Sound sound) {
        sound.dispose();
    }

    // might throw concurrent error?
    public void disposeAllSounds() {
        for(Sound s : this.sounds) {
            this.disposeSound(s);
        }
    }
}
