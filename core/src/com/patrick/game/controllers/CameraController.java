package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Entity;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Settings;

public class CameraController {

    private OrthographicCamera camera;

    public CameraController() {
        this.resetCamera();
    }

    public void move(Vector2 destination) {
        camera.translate(destination);
    }

    public void centerOnEntity(Entity e) {}

    public void calculatedZoomPan(Entity e) {
        final float xDiff;
    }

    public void zoomIn() {
        // no work
        camera.zoom -= .1;
        camera.update();
    }

    public void zoomOut() {
        // work
        camera.zoom += .1;
        camera.update();
    }

    public void resetCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.zoom = .97f;
        camera.position.y = camera.position.y - Settings.TILE_SIZE *2;
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void update() {
        camera.update();
    }
}
