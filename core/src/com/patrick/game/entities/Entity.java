package com.patrick.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Settings;

public class Entity implements Comparable<Entity>{

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
        return dir;
    }

    public void setDir(Direction dir) {
        this.lastDir = this.dir;
        this.dir = dir;
    }

    public Direction getLastDir() {
        return lastDir;
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
        return position;
    }

    public void setHeightGain(float heightGain) {
        this.heightGain = heightGain;
    }

    public float getHeightGain() {
        return heightGain;
    }

    public int getId() {
        return id;
    }

    public float getSpeed() {
        return speed;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public float getDecelSpeed() {
        return decelSpeed;
    }

    public float getVelocityY() {
        return -weight + heightGain;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Rectangle getCollider() {
        return collider;
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
//        if(this.grounded) {
        if (this.velocity > 0) this.velocity -= this.decelSpeed * delta;
        if (this.velocity < 0) this.velocity += this.decelSpeed * delta;
//        }
        if (this.heightGain > 0) this.heightGain -= this.decelSpeed * delta;
        if (this.heightGain < 0) this.heightGain += this.decelSpeed * delta;
        if (this.heightGain < 5 && this.heightGain > 0) this.heightGain = 0;
        if (this.heightGain > -5 && this.heightGain < 0) this.heightGain = 0;
        if (this.velocity < 5 && this.velocity > 0) this.velocity = 0;
        if (this.velocity > -5f && this.velocity < 0) this.velocity = 0;
        if (this.collider != null)
                this.collider.setPosition(this.position);
    }

    public void draw(Batch batch, ShapeRenderer renderer) {
//        if (Settings.DEBUG_COLLISION && this.collider != null) {
//            renderer.begin(ShapeRenderer.ShapeType.Line);
//            renderer.setColor((this.debugColor != null ? debugColor : Color.BLUE));
//            renderer.rect(collider.x, collider.y, collider.width, collider.height);
//            renderer.end();
//        }
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