package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

public class CameraController {

    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;

    public CameraController() {
        this.resetCamera();
    }

    public void move(Vector2 destination) {
        this.camera.translate(destination);
    }

    public void centerOnEntity(Entity e) {}

    public void calculatedZoomPan(Entity e) {}

    public void moveCameraTowards(Entity e, float speed, float delta) {
        if(this.camera.zoom < .2f) return;
        this.camera.translate((e.x() - e.width() - (this.camera.position.x * .86f)) * delta, (e.y() - this.camera.position.y) * delta);
        this.zoomIn(delta / 2);
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
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.camera.zoom = .97f;
        this.camera.position.y = this.camera.position.y - Settings.TILE_SIZE *2;
        this.camera.update();

        this.uiCamera = new OrthographicCamera();
        this.uiCamera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        this.uiCamera.zoom = .97f;
        this.uiCamera.position.y = this.uiCamera.position.y - Settings.TILE_SIZE *2;
        this.uiCamera.update();
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public OrthographicCamera getUiCamera() {
        return this.uiCamera;
    }

    public void update() {
        this.camera.update();
        this.uiCamera.update();
    }
}
