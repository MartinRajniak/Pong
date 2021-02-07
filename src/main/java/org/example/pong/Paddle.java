package org.example.pong;

import java.awt.*;

public class Paddle implements Drawable {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 80;

    private final int SPEED = 2;

    public int x;
    public int y;
    private int dY = 0;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void update() {
        y += dY;
    }

    public void handleScreenCollision(int topY, int bottomY) {
        if (this.y <= topY) {
            this.y += SPEED;
        } else if (this.y + HEIGHT >= bottomY) {
            this.y -= SPEED;
        }
    }

    public void up() {
        dY = -SPEED;
    }

    public void down() {
        dY = SPEED;
    }

    public void stop() {
        dY = 0;
    }
}
