package com.patrick.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraController {

    private OrthographicCamera camera;

    public CameraController() {
        this.resetCamera();
    }

    public void move(Vector2 destination) {
        camera.translate(destination);
    }

    public void resetCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom += 1;
        camera.update();
    }


    public OrthographicCamera getCamera() {
        return camera;
    }

    public void update() {
        camera.update();
    }
}
