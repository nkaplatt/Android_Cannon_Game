package com.exeter.np326.cannongame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by nickplatt on 15/03/2017.
 */

public class CannonSprite extends Sprite {
    float x, y;
    double angle;

    /**
     * Constructor for CannonSprite
     */
    public CannonSprite() {

    }

    /**
     * Method to draw cannon sprite on screen
     * @param canvas
     * @param cannon
     */
    public void draw(Canvas canvas, Bitmap cannon) {
        cannon = Bitmap.createScaledBitmap(cannon, cannon.getWidth()/3, cannon.getHeight()/3, false);
        canvas.drawBitmap(cannon, (gameWidth/2)-(cannon.getWidth()/2), gameHeight-(cannon.getHeight() + 80), null);
        //rotate(angle);
    }

    /**
     * Method finds the angles and sides in order to rotate the cannon
     * in the direction of click/ cannonball fire.
     * @param x
     * @param y
     */
    public void findSides(float x, float y) {
        float opp = (gameWidth/2) - x;
        float adj = gameHeight - y;
        double hyp = Math.sqrt((opp*opp)+(adj*adj));
        angle = Math.asin(opp/hyp);
        System.out.println("Angle in radians " + angle);
    }

    /**
     * @param theta
     */
    public void rotate(double theta) {
        // rotate this vector by the angle made to the horizontal by this line
        // theta is in radians
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);

        float nx = x * cosTheta - y * sinTheta;
        float ny = x * sinTheta + y * cosTheta;

        x = nx;
        y = ny;
    }
}
