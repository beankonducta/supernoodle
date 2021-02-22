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
        this.resetCamera();
    }

    public void move(Vector2 destination) {
        this.camera.translate(destination);
    }

    public void moveCameraTowards(Entity e, float speed, float delta) {
        if (this.camera.zoom > .2f) {
            this.zoomIn(delta / 4);
        }
        Vector2 diff = new Vector2(e.x() - this.camera.position.x, e.y() - this.camera.position.y);
        if (Math.abs(diff.x) > 4)
            this.camera.translate(diff.x * delta, 0);
        if (Math.abs(diff.y) > 4)
            this.camera.translate(0, diff.y * delta);
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
        this.camera = new OrthographicCamera();
        this.uiCamera = new OrthographicCamera();
        this.camera.setToOrtho(false, Settings.VIEWPORT_WIDTH, Settings.VIEWPORT_HEIGHT);
        this.camera.zoom = .98f;
        this.camera.update();
        this.uiCamera.setToOrtho(false, Settings.VIEWPORT_WIDTH, Settings.VIEWPORT_HEIGHT);
        this.uiCamera.zoom = 1;
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
