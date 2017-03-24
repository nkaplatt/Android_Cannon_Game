package com.exeter.np326.cannongame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by nickplatt on 15/03/2017.
 */

public class CannonBallSprite extends Sprite {
    private int scale = gameWidth/10; // cannonball scale
    private boolean hitBlocker = false;
    private int start = gameWidth/2;
    private int halfBall, ballHeight;
    private float stepX ,stepY, gradient;
    int level;
    public boolean offScreen = false;


    /**
     * Constructor for cannonball sprite
     */
    public CannonBallSprite() {
        this.stepX = 0;
        this.stepY = 0;
    }

    /**
     * Method that draws the cannonball sprite - takes into account
     * that the call is drawn from the bottom middle of screen
     * @param canvas
     * @param ball
     */
    public void draw(Canvas canvas, Bitmap ball) {
        ball = Bitmap.createScaledBitmap(ball, scale, scale, false); // scale the cannonball for a device
        halfBall = ball.getWidth()/2; // so the center of the ball is center in X axis
        ballHeight = ball.getHeight(); // the cannonball bitmap height
        canvas.drawBitmap(ball, (start - halfBall)+stepX, (gameHeight-ballHeight)+stepY, null); // draws the cannonball this will need updating along the gradient

        stepX -= (1/gradient)*(gameWidth/20); // move along X axis
        if (hitBlocker) {
            // blocker hit so bounce back
            stepY = (-gradient * stepX)-((gameHeight/3)*4-200); // move ball along gradient Y axis downwards
        } else {
            stepY = gradient * stepX; // move ball along gradient Y axis
        }
        cannonOffScreen(stepX, stepY); // check to see if cannonballs gone off screen
    }

    /**
     * Method that detects when a cannonball sprite leaves the screen
     * used for then removing the cannonball from the cannonballsprite array
     * @param horizontal
     * @param verticle
     */
    public void cannonOffScreen(float horizontal, float verticle) {
        if ((gameWidth/2)+horizontal >= gameWidth || ((gameWidth/2)+horizontal <= 0)) {
            offScreen = true;
        } else if (-verticle > gameHeight || verticle > 0) {
            offScreen = true;
        }
    }

    /**
     * position maps a rectangle where the blocker location is currently
     * used for collision detection.
     * @return
     */
    public RectF position() {
        float bTopLeftX = (start - halfBall)+stepX;
        float bTopLeftY = (gameHeight-ballHeight)+stepY;
        RectF space = new RectF(bTopLeftX, bTopLeftY, bTopLeftX+(2*halfBall), bTopLeftY+ballHeight);
        return space;
    }

    /**
     * Method uses parameters from onclick even in spriteview. Used for
     * mapping direction of ball path.
     * @param x
     * @param y
     */
    public void clickCoordinates(float x, float y) {
        this.start = gameWidth/2;
        float top = y - gameHeight; // used for working out gradient
        float bottom = x - (start-halfBall); // used for working out gradient
        this.gradient = (top)/(bottom);
    }

    /**
     * Method used for returning if the blocker has been hit.
     */
    public void hitBlocker() {
        hitBlocker = true;
    }
}
