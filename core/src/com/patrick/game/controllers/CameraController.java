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
        this.camera.translate(destination);
    }

    public void centerOnEntity(Entity e) {}

    public void calculatedZoomPan(Entity e) {}

    public void zoomIn() {
        // no work
        this.camera.zoom -= .1;
        this.camera.update();
    }

    public void zoomOut() {
        // work
        this.camera.zoom += .1;
        this.camera.update();
    }

    public void resetCamera() {
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
