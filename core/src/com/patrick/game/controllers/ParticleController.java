package com.patrick.game.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Map;
import com.patrick.game.entities.Particle;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Math;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

public class ParticleController {

    public ParticleController() {
    }

    public void randomMoveParticlesAdd(Map map, Entity e, int max) {
        for (int i = 0; i < com.patrick.game.util.Math.RANDOM_BETWEEN(max / 3, max); i++)
            map.addParticle(new Particle(
                    Resources.GRASS_ANIM_REGION[0][com.patrick.game.util.Math.RANDOM_BETWEEN(0, Resources.GRASS_ANIM_REGION[0].length - 1)],
                    new Vector2(e.x() + (Settings.TILE_SIZE / 2) + (e.getDir() == Direction.LEFT ? 12 : 0), e.y() + 2),
                    Settings.DEFAULT_PARTICLE_WEIGHT,
                    Settings.PLAYER_DECEL_SPEED));
    }

    public void randomWinParticlesAdd(Map map, Entity e, int max) {
        for (int i = 0; i < com.patrick.game.util.Math.RANDOM_BETWEEN(max / 3, max); i++)
            map.addParticle(new Particle(
                    Resources.RAIN_ANIM_REGION[0][com.patrick.game.util.Math.RANDOM_BETWEEN(0, Resources.RAIN_ANIM_REGION[0].length - 1)],
                    new Vector2(e.x() + (Settings.TILE_SIZE / 2) + 8, e.y() + e.height() + 16),
                    Math.FLOAT_RANDOM_BETWEEN(Settings.DEFAULT_PARTICLE_WEIGHT / 10, Settings.DEFAULT_PARTICLE_WEIGHT),
                    Math.FLOAT_RANDOM_BETWEEN(Settings.PLAYER_DECEL_SPEED / 10, Settings.PLAYER_DECEL_SPEED),
                    Math.RANDOM_BETWEEN(-700, 700),
                    Math.RANDOM_BETWEEN(0, 700), 30));
    }

    public void bowlParticlesAdd(Map map, Entity e, int max) {
        for (int i = 0; i < com.patrick.game.util.Math.RANDOM_BETWEEN(max / 2, max); i++)
            map.addParticle(new Particle(
                    Resources.BOWL_ANIM_REGION[0][com.patrick.game.util.Math.RANDOM_BETWEEN(0, Resources.BOWL_ANIM_REGION[0].length - 1)],
                    new Vector2(e.x() + (Settings.TILE_SIZE / 2), e.y()),
                    Settings.DEFAULT_PARTICLE_WEIGHT,
                    Settings.PLAYER_DECEL_SPEED / 3));
    }

    public void sweatParticlesAdd(Map map, Entity e, int max) {
        int random = com.patrick.game.util.Math.RANDOM_BETWEEN(0, max);
        if (random == max)
            map.addParticle(new Particle(
                    Resources.RAIN_ANIM_REGION[0][com.patrick.game.util.Math.RANDOM_BETWEEN(0, Resources.RAIN_ANIM_REGION[0].length - 1)],
                    new Vector2(e.x() + (Settings.TILE_SIZE / 2) + 8, e.y() + e.height() + 8),
                    Settings.DEFAULT_PARTICLE_WEIGHT,
                    Settings.PLAYER_DECEL_SPEED / 10,
                    e.getVelocity(),
                    0, 30));
    }

    public void rainParticlesAdd(Map map, OrthographicCamera camera, int max) {
        int random = com.patrick.game.util.Math.RANDOM_BETWEEN(0, max);
        if (random == max)
            for (int i = 0; i < max; i++)
                map.addParticle(new Particle(
                        Resources.RAIN_ANIM_REGION[0][com.patrick.game.util.Math.RANDOM_BETWEEN(0, Resources.RAIN_ANIM_REGION[0].length - 1)],
                        new Vector2(Math.RANDOM_BETWEEN(0, 1024), 500),
                        Settings.DEFAULT_PARTICLE_WEIGHT,
                        Settings.PLAYER_DECEL_SPEED,
                        0,
                        0, 360));
    }
}
