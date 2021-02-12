package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

public class CameraController {

    private OrthographicCamera camera;

    public CameraController() {
        this.resetCamera();
    }

    public void move(Vector2 destination) {
        this.camera.translate(destination);
    }

    public void centerOnEntity(Entity e) {}

    public void calculatedZoomPan(Entity e) {}

    public void moveCameraTowards(Entity e, float speed, float delta) {
        float xDir = 0;
        float yDir = 0;
        xDir = e.x() > this.camera.position.x + 10 ? e.x() : -e.x();
        yDir = e.y() > this.camera.position.y + 10 ? e.y() : -e.y();
        System.out.println(e.y() + " : "+this.camera.position.y);
        this.camera.translate(speed * delta * xDir, speed * 10 * delta * yDir);
        if(this.camera.zoom > .2f) this.zoomIn(delta / 2);
        this.camera.update();
    }

    public void zoomIn(float delta) {
        // no work
        this.camera.zoom = this.camera.zoom -= delta;
        this.camera.update();
    }

    public void zoomOut(float delta) {
        // work
        this.camera.zoom += delta;
        this.camera.update();
    }

    public void resetCamera() {
        System.out.println("RESETTING");
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.camera.zoom = .97f;
        this.camera.position.y = this.camera.position.y - Settings.TILE_SIZE *2;
        this.camera.update();
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public void update() {
        this.camera.update();
    }
}
