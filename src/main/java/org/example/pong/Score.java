package org.example.pong;

import java.awt.*;

public class Score implements Drawable {

    private final int x;

    private int score = 0;

    public Score(int x) {
        this.x = x;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.WHITE);

        final Font font = graphics.getFont().deriveFont(40.0f);
        graphics.setFont(font);

        graphics.drawString(String.valueOf(score), x, 60);
    }

    public void addPoint() {
        score++;
    }
}
