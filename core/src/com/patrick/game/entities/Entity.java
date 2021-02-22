package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Settings;

public class Entity implements Comparable<Entity> {

    protected Direction dir, lastDir;
    protected Vector2 position;
    protected float speed;
    protected float velocity;
    protected float weight;
    protected float decelSpeed;
    protected float heightGain;
    protected TextureRegion texture;
    protected Rectangle collider;
    protected Rectangle floorCollider;
    protected int id;
    protected boolean grounded;
    protected float actionTimer;
    protected Color debugColor;

    public float getActionTimer() {
        return this.actionTimer;
    }

    public void incrementActionTimer() {
        this.actionTimer += 1f;
    }

    public void resetActionTimer() {
        this.actionTimer = 0;
    }

    public Rectangle getFloorCollider() {
        return this.floorCollider;
    }

    public Color getDebugColor() {
        return this.debugColor;
    }

    public Direction getDir() {
        return this.dir;
    }

    public void setDir(Direction dir) {
        this.lastDir = this.dir;
        this.dir = dir;
    }

    public Direction getLastDir() {
        return this.lastDir;
    }

    public void setLastDir(Direction dir) {
        this.lastDir = dir;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean getGrounded() {
        return this.grounded;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public float x() {
        return this.position.x;
    }

    public float y() {
        return this.position.y;
    }

    public float height() {
        if (this.collider == null) return 0;
        return this.collider.height;
    }

    public float width() {
        if (this.collider == null) return 0;
        return this.collider.width;
    }

    public void setHeightGain(float heightGain) {
        this.heightGain = heightGain;
    }

    public float getHeightGain() {
        return this.heightGain;
    }

    public int getId() {
        return this.id;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return this.weight;
    }

    public TextureRegion getTexture() {
        return this.texture;
    }

    public Rectangle getCollider() {
        return this.collider;
    }

    public Entity(Vector2 position, TextureRegion texture) {
        this.position = position;
        this.texture = texture;
        this.speed = 0;
        this.velocity = 0;
        this.weight = 0;
        this.decelSpeed = 0;
        this.heightGain = 0;
        this.id = -1;
    }

    public Entity(Vector2 position, float speed, float weight, float decelSpeed, TextureRegion texture) {
        this.position = position;
        this.speed = speed;
        this.decelSpeed = decelSpeed;
        this.weight = weight;
        this.texture = texture;
        this.velocity = 0;
        this.heightGain = 0;
        this.id = -1;
    }

    public Entity(Vector2 position, float speed, float weight, float decelSpeed, TextureRegion texture, int id) {
        this.position = position;
        this.speed = speed;
        this.decelSpeed = decelSpeed;
        this.weight = weight;
        this.texture = texture;
        this.id = id;
        this.velocity = 0;
        this.heightGain = 0;
    }

    public void update(float delta) {
        if (this.velocity > 0) this.velocity -= this.decelSpeed * delta;
        if (this.velocity < 0) this.velocity += this.decelSpeed * delta;
        if (this.heightGain > 0) this.heightGain -= this.decelSpeed * delta;
        if (this.heightGain < 0) this.heightGain += this.decelSpeed * delta;
        if (this.heightGain < 10 && this.heightGain > 0) this.heightGain = 0;
        if (this.heightGain > -10 && this.heightGain < 0) this.heightGain = 0;
        if (this.velocity < 10 && this.velocity > 0) this.velocity = 0;
        if (this.velocity > -10f && this.velocity < 0) this.velocity = 0;
        if (this.collider != null)
            this.collider.setPosition(this.position);
    }

    public void draw(Batch batch) {
    }

    public void move(Vector2 position) {
        this.position = this.position.add(position);
    }

    public void moveTo(Vector2 position) {
        this.position = position;
    }

    @Override
    public int compareTo(Entity e) {
        if (this.getId() == e.getId())
            return 0;
        else if (this.getId() > e.getId())
            return 1;
        else
            return -1;
    }
}