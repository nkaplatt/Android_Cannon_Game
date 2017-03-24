package com.exeter.np326.cannongame;

import java.util.ArrayList;

/**
 * Created by Nick on 18/03/2017.
 */

public class GameModel {
    ArrayList<TargetSprite> targetSprites;
    BlockerSprite blockerSprite;
    CannonSprite cannonSprite;
    ArrayList<CannonBallSprite> cannonballSprites;
    int score;
    int timeRemaining; // game starts with 10 seconds


    /**
     * Constructor of game model - sets game length, score, and
     * 4 types of sprites
     * @param level
     */
    public GameModel(int level) {
        System.out.println("Cannon game model is active");
        targetSprites(level);
        cannonSprite(); // one cannon needed
        blockerSprite(level);
        cannonballSprite(level);
        score = 0;
        timeRemaining = 10000;
    }

    /**
     * Method fetches coordinates of screen click to fire cannonball
     * @param x
     * @param y
     */
    public void clickFire(float x, float y) {
        // fire the cannonball?
        cannonballSprites.get(0).clickCoordinates(x, y);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void moveCannon(float x, float y) {
        cannonSprite.findSides(x, y);
    }

    /**
     * Method to check game over - if time is less than 0
     * @return
     */
    public boolean gameOver() {
        return timeRemaining <= 0;
    }

    /**
     * @param delay
     */
    public void update(int delay) {
        if (!gameOver()) {
            // check all targets broken and timer not 0
            timeRemaining -= delay;
        }
    }

    /**
     * Method creates array of targets depending of level
     * game then displays one at a time
     * @param level
     */
    private void targetSprites(int level) {
        targetSprites = new ArrayList<>(level);
        for(int i = 0; i < level*2; i++) {
            targetSprites.add(new TargetSprite(level));
        }
    }

    /**
     * Method to create cannon
     */
    private void cannonSprite() {
        cannonSprite = new CannonSprite();
    }

    /**
     * Method to create blocker
     * @param level
     */
    private void blockerSprite(int level) {
        blockerSprite = new BlockerSprite(level);
    }

    /**
     * Method creates cannonball array with a cannonball instantiated
     * game used this array to create new cannonball.
     * @param level
     */
    private void cannonballSprite(int level) {
        cannonballSprites = new ArrayList<>();
        cannonballSprites.add(new CannonBallSprite()); // initialise array with 1
    }
}
