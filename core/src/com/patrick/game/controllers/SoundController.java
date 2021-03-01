package com.patrick.game.controllers;

import com.badlogic.gdx.audio.Sound;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;

public class SoundController {

    private static List<Sound> sounds = new ArrayList<>();

    private static float SOUND_TIMER = 0f;
    private static float SOUND_TIMER_DELAY = .1f;

    /**
     * Attempts to play a sound via provided string. Adds to our sounds list so we can dispose later.
     *
     * @param s - string of sound to play
     */
    public static void playSound(String s) {
        Sound sound = Resources.SOUND(s);
        if (sound == null) return;
        if (!sounds.contains(sound)) sounds.add(sound);
        sound.play(Settings.SOUND_VOLUME);
    }

    /**
     * Plays a sound with a calculated offset (for stuff like walking, so the sounds aren't playing 60 times per second)
     *
     * @param s
     * @param delta
     */
    public static void playSoundOffset(String s, float delta) {
        if(SOUND_TIMER <= delta) {
            Sound sound = Resources.SOUND(s);
            if (sound == null) return;
            if (!sounds.contains(sound)) sounds.add(sound);
            sound.play(Settings.SOUND_VOLUME);
        }
        SOUND_TIMER += delta;
        if (SOUND_TIMER >= SOUND_TIMER_DELAY)
            SOUND_TIMER -= SOUND_TIMER_DELAY;
    }

    /**
     * Plays a sound and adds to our sounds list (so we can dispose it later).
     *
     * @param sound
     */
    public static void playSound(Sound sound) {
        if (!sounds.contains(sound)) sounds.add(sound);
        sound.play(Settings.SOUND_VOLUME);
    }

    /**
     * Stops the sound. Stops it in our sound list, if it exists.
     *
     * @param sound
     */
    public static void stopSound(Sound sound) {
        if (sounds.contains(sound)) sounds.get(sounds.indexOf(sound)).stop();
        else sound.stop();
    }

    /**
     * Disposes the sound.
     *
     * @param sound
     */
    private static void disposeSound(Sound sound) {
        sound.dispose();
    }

    /**
     * Disposes all sounds in our list.
     */
    public static void disposeAllSounds() {
        for (Sound s : sounds) {
            disposeSound(s);
        }
    }
}
