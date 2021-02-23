package com.patrick.game.controllers;

import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundController {

    private List<Sound> sounds;

    public SoundController() {
        this.sounds = new ArrayList<>();
    }

    /**
     * Plays a sound and adds to our sounds list (so we can dispose it later).
     *
     * @param sound
     */
    public void playSound(Sound sound) {
        if(!this.sounds.contains(sound)) this.sounds.add(sound);
        sound.play();
    }

    /**
     * Stops the sound. Stops it in our sound list, if it exists.
     *
     * @param sound
     */
    public void stopSound(Sound sound) {
        if(this.sounds.contains(sound)) this.sounds.get(this.sounds.indexOf(sound)).stop();
        else sound.stop();
    }

    /**
     * Disposes the sound.
     *
     * @param sound
     */
    private void disposeSound(Sound sound) {
        sound.dispose();
    }

    /**
     * Disposes all sounds in our list.
     *
     */
    public void disposeAllSounds() {
        for(Sound s : this.sounds) {
            this.disposeSound(s);
        }
    }
}
