package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

public class CameraController {

    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;

    public CameraController() {
        this.camera = new OrthographicCamera();
        this.uiCamera = new OrthographicCamera();
        this.resetCamera();
    }

    public void move(Vector2 destination) {
        this.camera.translate(destination);
    }

    public void centerOnEntity(Entity e) {
    }

    public void calculatedZoomPan(Entity e) {
    }

    public void moveCameraTowards(Entity e, float speed, float delta) {
        if (this.camera.zoom < .2f) return;
        this.camera.translate((e.x() - e.width() - (this.camera.position.x * .86f)) * delta, (e.y() - this.camera.position.y) * delta);
        this.zoomIn(delta / 2);
        this.camera.update();
    }

    public void zoomIn(float delta) {
        this.camera.zoom -= delta;
        this.camera.update();
    }

    public void zoomOut(float delta) {
        this.camera.zoom += delta;
        this.camera.update();
    }

    public void resetCamera() {
        this.camera.setToOrtho(false, Settings.VIEWPORT_WIDTH, Settings.VIEWPORT_HEIGHT);
        this.camera.update();
        this.uiCamera.setToOrtho(false, Settings.VIEWPORT_WIDTH, Settings.VIEWPORT_HEIGHT);
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
