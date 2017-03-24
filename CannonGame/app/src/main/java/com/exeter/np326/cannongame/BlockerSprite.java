package com.exeter.np326.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by nickplatt on 15/03/2017.
 */

public class BlockerSprite extends Sprite {
    private int thickness = gameHeight/3; // places the blocker a 1/3 of the way down the screen
    private int blockerWidth = gameWidth/6;
    private int move = placement.nextInt(gameWidth - blockerWidth);
    private int speed;

    /**
     * Constructor for BlockerSprites
     * @param speed
     */
    public BlockerSprite(int speed) {
        this.speed = speed*(gameWidth/200); // speed of target
    }

    /**
     * Method to draw the blocker object onto a canvas
     * Method has canvas passed to it - calls an update method to update
     * location for blocker
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        Paint blockerSprite = new Paint();
        blockerSprite.setARGB(201, 158, 34, 0);
        RectF blocker = new RectF(move, thickness, blockerWidth+move, (thickness+50));
        canvas.drawRect(blocker, blockerSprite);
        move = update(blocker);
    }

    /**
     * Method to update location for redrawing of blocker sprite
     * @param blocker
     * @return
     */
    public int update(RectF blocker) {
        if (move >= (blockerWidth*5) || move + speed < 0) {
            speed = -speed;
        }
        move += speed;
        return move;
    }

    /**
     * position maps a rectangle where the blocker location is currently
     * used for collision detection.
     * @return
     */
    public RectF position() {
        RectF space = new RectF(move, thickness, blockerWidth+move, (thickness+50));
        return space;
    }
}
