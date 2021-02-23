package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Math;
import com.patrick.game.util.Settings;

public class Particle extends StaticEntity {

    protected int timeToLive;

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getTimeToLive() {
        return this.timeToLive;
    }

    public Particle(TextureRegion texture, Vector2 position, float weight, float decelSpeed, float velocity, float heightGain, int timeToLive) {
        super(position, 500, weight, decelSpeed, texture);
        this.velocity = velocity;
        this.heightGain = heightGain;
        this.id = Math.RANDOM_BETWEEN(100, 100000);
        this.collider = new Rectangle(position.x, position.y, 2, 2);
        this.debugColor = Color.ORANGE;
        this.timeToLive = timeToLive;
    }

    public Particle(TextureRegion texture, Vector2 position, float weight, float decelSpeed) {
        super(position, 500, weight, decelSpeed, texture);
        this.velocity = Math.RANDOM_BETWEEN((int) -Settings.MAX_PARTICLE_VELOCITY, (int) Settings.MAX_PARTICLE_VELOCITY);
        this.heightGain = Math.RANDOM_BETWEEN((int) Settings.MAX_PARTICLE_HEIGHT_GAIN / 2, (int) Settings.MAX_PARTICLE_HEIGHT_GAIN);
        this.timeToLive = Math.RANDOM_BETWEEN(1, Settings.MAX_PARTICLE_TIME_TO_LIVE);
        this.id = Math.RANDOM_BETWEEN(100, 100000);
        this.collider = new Rectangle(position.x, position.y, 1, 1);
        this.debugColor = Color.ORANGE;
    }

    /**
     * Update the particle, lower time to live so the particle can go away eventually.
     *
     * @param delta
     */
    public void update(float delta) {
        super.update(delta);
        this.timeToLive--;
    }

    /**
     * Draw the particle.
     *
     * @param batch
     */
    public void draw(Batch batch) {
        super.draw(batch);
    }

    /**
     * Move the particle by provided position.
     *
     * @param position
     */
    public void move(Vector2 position) {
        super.move(position);
    }
}
