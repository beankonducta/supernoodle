package com.patrick.game.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.util.Settings;

public class CameraController {

    // TODO: consider making this singleton

    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;

    public OrthographicCamera getCamera() {
        return this.camera;
    }
    public OrthographicCamera getUiCamera() {
        return this.uiCamera;
    }

    public CameraController() {
        this.resetCamera();
    }

    /**
     * Translates camera to provided destination.
     *
     * @param destination
     */
    public void move(Vector2 destination) {
        this.camera.translate(destination);
    }

    /**
     * Moves camera towards and zooms in on specified entity at specified speed. Stops 4 pixels before
     * the entity to avoid screen shake from constantly trying to move back and forth to find the entity.
     *
     * @param e
     * @param speed
     * @param delta
     */
    public void moveCameraTowards(Entity e, float speed, float delta) {
        final Vector2 diff = new Vector2(e.x() - this.camera.position.x, e.y() - this.camera.position.y);
        if (this.camera.zoom > .2f)
            this.zoomIn(delta / 4);
        if (Math.abs(diff.x) > 4)
            this.camera.translate(diff.x * delta, 0);
        if (Math.abs(diff.y) > 4)
            this.camera.translate(0, diff.y * delta);
        this.camera.update();
    }

    /**
     * Zooms in the main game camera.
     *
     * @param delta
     */
    public void zoomIn(float delta) {
        this.camera.zoom -= delta;
        this.camera.update();
    }

    /**
     * Zooms out the main game camera.
     *
     * @param delta
     */
    public void zoomOut(float delta) {
        this.camera.zoom += delta;
        this.camera.update();
    }

    /**
     * Resets both the game camera and the UI camera.
     *
     */
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

    /**
     * Updates both the game camera and the UI camera.
     *
     */
    public void update() {
        this.camera.update();
        this.uiCamera.update();
    }
}
