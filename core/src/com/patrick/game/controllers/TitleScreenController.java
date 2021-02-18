package com.patrick.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.Cloud;
import com.patrick.game.entities.Ingredient;
import com.patrick.game.entities.Player;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class TitleScreenController {

    private CameraController cameraController;

    private Player playerOne;
    private Player playerTwo;

    private Ingredient ingredientOne;
    private Ingredient ingredientTwo;

    private List<Cloud> clouds;

    private Vector2 logoPosition;
    private Vector2 playerOnePosition;
    private Vector2 playerTwoPosition;
    private Vector2 playerOneStartPlaquePosition;
    private Vector2 playerTwoStartPlaquePosition;

    private boolean playerOneReady;
    private boolean playerTwoReady;

    private float logoDirection;

    public Vector2 getLogoPosition() {
        return this.logoPosition;
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    public Ingredient getIngredientOne() {
        return this.ingredientOne;
    }

    public Ingredient getIngredientTwo() {
        return this.ingredientTwo;
    }

    public List<Cloud> getClouds() {
        return this.clouds;
    }

    public Vector2 getPlayerOneStartPlaquePosition() {
        return this.playerOneStartPlaquePosition;
    }

    public Vector2 getPlayerTwoStartPlaquePosition() {
        return this.playerTwoStartPlaquePosition;
    }

    public boolean isPlayerOneReady() {
        return this.playerOneReady;
    }

    public boolean isPlayerTwoReady() {
        return this.playerTwoReady;
    }

    public void setPlayerOneReady(boolean playerOneReady) {
        this.playerOneReady = playerOneReady;
    }

    public void setPlayerTwoReady(boolean playerTwoReady) {
        this.playerTwoReady = playerTwoReady;
    }

    public TitleScreenController(CameraController cameraController) {
        this.cameraController = cameraController;
        this.clouds = new ArrayList<>();
        this.cameraController.zoomIn(.5f);
        this.playerTwoStartPlaquePosition = new Vector2(cameraController.getCamera().viewportWidth / 2 + 128, cameraController.getCamera().viewportHeight);
        this.playerOneStartPlaquePosition = new Vector2(cameraController.getCamera().viewportWidth / 2 - Resources.START_PLAQUE_WIDTH - 128, cameraController.getCamera().viewportHeight);
        this.logoPosition = new Vector2(this.cameraController.getCamera().viewportWidth / 2 - 112, this.cameraController.getCamera().viewportHeight * .5f);
        this.playerOnePosition = new Vector2(this.cameraController.getCamera().viewportWidth / 2 - 16, this.cameraController.getCamera().viewportHeight * .42f);
        this.playerTwoPosition = new Vector2(this.cameraController.getCamera().viewportWidth / 2, this.cameraController.getCamera().viewportHeight * .34f);
        this.playerOne = new Player(new Vector2(100, 100), 0, 0, 0, 1);
        this.playerTwo = new Player(new Vector2(this.cameraController.getCamera().viewportWidth - 100, 25), 0, 0, 0, 2);
        this.ingredientOne = new Ingredient(new Vector2(100, 100), 0, 0, 0, 3);
        this.ingredientTwo = new Ingredient(new Vector2(this.cameraController.getCamera().viewportWidth - 100, 25), 0, 0, 0, 4);
        this.playerTwo.setDir(Direction.LEFT);
        this.playerOne.setDir(Direction.RIGHT);
        this.playerOne.setForcePlayAnimation(true);
        this.playerTwo.setForcePlayAnimation(true);
        this.playerOne.changeAnimation("JUMP", true);
        this.playerTwo.changeAnimation("JUMP", true);
        this.playerOne.setIngredient(this.ingredientOne);
        this.playerTwo.setIngredient(this.ingredientTwo);
        this.logoDirection = -1;
        this.fillClouds();
    }

    private void fillClouds() {
        for (int c = 0; c < Settings.CLOUD_COUNT; c++) {
            int x = com.patrick.game.util.Math.RANDOM_BETWEEN(0, (int)this.cameraController.getCamera().viewportWidth);
            int y = com.patrick.game.util.Math.RANDOM_BETWEEN(0, (int)this.cameraController.getCamera().viewportHeight);
            this.clouds.add(new Cloud(new Vector2(x, y), com.patrick.game.util.Math.RANDOM_BETWEEN(Settings.CLOUD_MIN_SPEED, Settings.CLOUD_MAX_SPEED), com.patrick.game.util.Math.EITHER_OR(-100, 100)));
        }
    }

    public void updateScreenPositions(float delta) {
        this.playerOne.update(delta);
        this.playerTwo.update(delta);
        float logoDiff = Math.abs(this.logoPosition.y - (this.cameraController.getCamera().viewportHeight * .51f));
        float plaqueDiff = Math.abs(this.playerOneStartPlaquePosition.y - (this.cameraController.getCamera().viewportHeight * .675f));
        Vector2 playerOneDiff = new Vector2(this.playerOnePosition.x - this.playerOne.x(), this.playerOnePosition.y - this.playerOne.y());
        Vector2 playerTwoDiff = new Vector2(this.playerTwoPosition.x - this.playerTwo.x(), this.playerTwoPosition.y - this.playerTwo.y());
        if (logoDiff < 1 && this.logoDirection == 1) this.logoDirection = -1;
        if (logoDiff > 50 && this.logoDirection == -1) this.logoDirection = 1;
        this.playerOneStartPlaquePosition.add(new Vector2(0, -plaqueDiff * delta));
        this.playerTwoStartPlaquePosition.add(new Vector2(0, -plaqueDiff * delta));
        this.logoPosition.add(new Vector2(0, this.logoDirection * logoDiff * delta));
        if (playerOneDiff.x > 2 || playerOneDiff.y > 2) {
            this.playerOne.move(new Vector2(playerOneDiff.x * delta, playerOneDiff.y * delta));
        }
        if (playerTwoDiff.x > .01 || playerTwoDiff.y > .01) {
            this.playerTwo.move(new Vector2(playerTwoDiff.x * delta, playerTwoDiff.y * delta));
        }
    }

}
