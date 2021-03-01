package com.patrick.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.patrick.game.entities.*;
import com.patrick.game.util.Direction;
import com.patrick.game.util.Misc;
import com.patrick.game.util.Resources;
import com.patrick.game.util.Settings;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class TitleScreenController {

    private CameraController cameraController;
    private ParticleController particleController;

    private Player playerOne;
    private Player playerTwo;

    private Ingredient ingredientOne;
    private Ingredient ingredientTwo;

    private List<Cloud> clouds;
    private List<Particle> particles;

    private Vector2 logoPosition;
    private Vector2 playerOnePosition;
    private Vector2 playerTwoPosition;
    private Vector2 playerOneStartPlaquePosition;
    private Vector2 playerTwoStartPlaquePosition;

    private boolean playerOneReady;
    private boolean playerTwoReady;

    private float logoDirection;

    private int startTimer;

    private int winningBowlId;

    public int getStartTimer() {
        return this.startTimer;
    }

    public void setStartTimer(int startTimer) {
        this.startTimer = startTimer;
    }

    public void incrementStartTimer() {
        this.startTimer++;
    }

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

    public List<Particle> getParticles() { return this.particles; }

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

    /**
     * Sets player one ready if player one's plaque has fully animated / lowered.
     *
     * @param playerOneReady
     */
    public void setPlayerOneReady(boolean playerOneReady) {
        if (this.playerOneStartPlaquePosition.y < this.cameraController.getCamera().viewportHeight - 175) {
            this.playerOneReady = playerOneReady;
            SoundController.playSound("win");
        }
    }

    /**
     * Sets player two ready if player two's plaque has fully animated / lowered.
     *
     * @param playerTwoReady
     */
    public void setPlayerTwoReady(boolean playerTwoReady) {
        if (this.playerTwoStartPlaquePosition.y < this.cameraController.getCamera().viewportHeight - 175) {
            this.playerTwoReady = playerTwoReady;
            SoundController.playSound("win");
        }
    }

    public TitleScreenController(CameraController cameraController, ParticleController particleController, int winningBowlId) {
        this.cameraController = cameraController;
        this.particleController = particleController;
        this.clouds = new ArrayList<>();
        this.particles = new ArrayList<>();
        this.cameraController.zoomIn(.5f);
        this.playerTwoStartPlaquePosition = new Vector2(cameraController.getCamera().viewportWidth / 2 + 128, cameraController.getCamera().viewportHeight);
        this.playerOneStartPlaquePosition = new Vector2(cameraController.getCamera().viewportWidth / 2 - Resources.START_PLAQUE_WIDTH - 128, cameraController.getCamera().viewportHeight);
        this.logoPosition = new Vector2(this.cameraController.getCamera().viewportWidth / 2 - 112, this.cameraController.getCamera().viewportHeight * .5f);
        this.playerOnePosition = new Vector2(this.cameraController.getCamera().viewportWidth / 2 - 32, this.cameraController.getCamera().viewportHeight * .42f);
        this.playerTwoPosition = new Vector2(this.cameraController.getCamera().viewportWidth / 2, this.cameraController.getCamera().viewportHeight * .34f);
        this.ingredientOne = new Ingredient(new Vector2(100, 100), 0, 0, 0, 3);
        this.ingredientTwo = new Ingredient(new Vector2(this.cameraController.getCamera().viewportWidth - 100, 25), 0, 0, 0, 4);
        this.logoDirection = -1;
        this.startTimer = 0;
        this.winningBowlId = winningBowlId;
        this.initPlayers();
        this.fillClouds();
    }

    public Player getWinningPlayer(int winningBowlId) {
        if(Misc.PLAYER_BOWL_MATCH_ID(this.playerOne.getId(), winningBowlId))
            return this.playerOne;
        else if(Misc.PLAYER_BOWL_MATCH_ID(this.playerTwo.getId(), winningBowlId))
            return this.playerTwo;
        return null;
    }

    /**
     * Inits the players.
     *
     */
    private void initPlayers() {
        this.playerOne = new Player(new Vector2(100, 100), 0, 0, 0, 1);
        this.playerTwo = new Player(new Vector2(this.cameraController.getCamera().viewportWidth - 100, 25), 0, 0, 0, 2);
        this.playerTwo.setDir(Direction.LEFT);
        this.playerOne.setDir(Direction.RIGHT);
        this.playerOne.setForcePlayAnimation(true);
        this.playerTwo.setForcePlayAnimation(true);
        this.playerOne.changeAnimation("JUMP", true);
        this.playerTwo.changeAnimation("JUMP", true);
        this.playerOne.setIngredient(this.ingredientOne);
        this.playerTwo.setIngredient(this.ingredientTwo);
    }

    private void resetPlayerPositions() {
        this.playerTwoPosition = new Vector2(100, this.cameraController.getCamera().viewportHeight + 100);
        this.playerOnePosition = new Vector2(this.cameraController.getCamera().viewportWidth - 100, this.cameraController.getCamera().viewportHeight + 100);
    }

    /**
     * Adds the clouds to our title screen.
     *
     */
    private void fillClouds() {
        for (int c = 0; c < Settings.CLOUD_COUNT; c++) {
            final int x = com.patrick.game.util.Math.RANDOM_BETWEEN(0, (int) this.cameraController.getCamera().viewportWidth);
            final int y = com.patrick.game.util.Math.RANDOM_BETWEEN(0, (int) this.cameraController.getCamera().viewportHeight);
            this.clouds.add(new Cloud(new Vector2(x, y), com.patrick.game.util.Math.RANDOM_BETWEEN(Settings.CLOUD_MIN_SPEED, Settings.CLOUD_MAX_SPEED), com.patrick.game.util.Math.EITHER_OR(-100, 100)));
        }
    }

    /**
     * Updates the active map's particle and effects list by removing any 'dead' particles.
     *
     * @param delta
     */
    private void removeParticles(float delta) {
        List<Particle> particlesToRemove = new ArrayList<>();
        for (Particle p : this.particles) {
            if (p.getTimeToLive() <= 0) particlesToRemove.add(p);
        }
        for (Particle p : particlesToRemove) {
            this.particles.remove(p);
        }
    }

    /**
     * Updates the positions of our title screens elements. Used for bouncing effects, pulling graphics on screen, etc.
     *
     * @param delta
     */
    public void updateScreenPositions(float delta) {
        this.playerOne.update(delta);
        this.playerTwo.update(delta);
        this.removeParticles(delta);
        if(this.startTimer == 6)
            this.resetPlayerPositions();
        this.particleController.titleWinParticlesAdd(this.particles, this.getWinningPlayer(this.winningBowlId), 100);
        final float logoDiff = Math.abs(this.logoPosition.y - (this.cameraController.getCamera().viewportHeight * .51f));
        final float plaqueDiff = Math.abs(this.playerOneStartPlaquePosition.y - (this.cameraController.getCamera().viewportHeight * .675f));
        final Vector2 playerOneDiff = new Vector2(this.playerOnePosition.x - this.playerOne.x(), this.playerOnePosition.y - this.playerOne.y());
        final Vector2 playerTwoDiff = new Vector2(this.playerTwoPosition.x - this.playerTwo.x(), this.playerTwoPosition.y - this.playerTwo.y());
        if (logoDiff < 1 && this.logoDirection == 1) this.logoDirection = -1;
        if (logoDiff > 50 && this.logoDirection == -1) this.logoDirection = 1;
        if (this.startTimer == 0) {
            this.playerOneStartPlaquePosition.add(new Vector2(0, -plaqueDiff * delta));
            this.playerTwoStartPlaquePosition.add(new Vector2(0, -plaqueDiff * delta));
        } else {
            this.playerOneStartPlaquePosition.sub(new Vector2(0, -100 * delta));
            this.playerTwoStartPlaquePosition.sub(new Vector2(0, -100 * delta));
            this.cameraController.zoomIn(delta / 25);
        }
        this.logoPosition.add(new Vector2(0, this.logoDirection * logoDiff * delta));
        if (playerOneDiff.x > 2 || playerOneDiff.y > 2) {
            this.playerOne.move(new Vector2(playerOneDiff.x * delta, playerOneDiff.y * delta));
        }
        if (playerTwoDiff.x > .01 || playerTwoDiff.y > .01) {
            this.playerTwo.move(new Vector2(playerTwoDiff.x * delta, playerTwoDiff.y * delta));
        }
    }
}