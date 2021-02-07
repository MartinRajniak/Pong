package org.example.pong;

import java.awt.*;

public class Ball implements Drawable {

    private static final int RADIUS = 10;

    private static final int SPEED = 1;

    public int x;
    public int y;

    private int initialX;
    private int initialY;
    private int dX = SPEED;
    private int dY = SPEED;

    public Ball(int x, int y) {
        initialX = x;
        initialY = y;
        reset();
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillOval(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    public void update() {
        x += dX;
        y += dY;
    }

    public int handleScreenCollision(int leftX, int topY, int rightX, int bottomY) {
        if (this.x - RADIUS < leftX) {
            reset();
            return -1;
        } else if (this.x + RADIUS > rightX) {
            reset();
            return 1;
        }

        if (this.y - RADIUS < topY || this.y + RADIUS > bottomY) {
            dY = -dY;
        }
        return 0;
    }

    private void reset() {
        x = initialX;
        y = initialY;
    }

    public void checkCollision(int leftX, int topY, int rightX, int bottomY) {
        // First condition for the left paddle and second condition to make sure that it isn't true for the right paddle.
        if (this.x - RADIUS <= rightX && this.x > leftX) {
            if (isSameHeight(topY, bottomY)) {
                hit();
            }
        }

        // First condition for the right paddle and second condition to make sure that it isn't true for the left paddle.
        if (this.x + RADIUS >= leftX && this.x <= rightX) {
            if (isSameHeight(topY, bottomY)) {
                hit();
            }
        }
    }

    private boolean isSameHeight(int topY, int bottomY) {
        return this.y >= topY && this.y <= bottomY;
    }

    private void hit() {
        dX = -dX;
    }
}
