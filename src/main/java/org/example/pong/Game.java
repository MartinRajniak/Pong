package org.example.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends KeyAdapter {

    private final GameWindow gameWindow;
    private final int screenHeight;
    private final int screenWidth;

    private final Paddle paddle1;
    private final Paddle paddle2;
    private final Ball ball;
    private final Score score1;
    private final Score score2;

    public static void main(String[] args) {
        new Game().start();
    }

    public Game() {
        gameWindow = new GameWindow();
        screenHeight = gameWindow.getContentPane().getHeight();
        screenWidth = gameWindow.getContentPane().getWidth();

        paddle1 = new Paddle(26, screenHeight / 2);
        paddle2 = new Paddle(screenWidth - 48, screenHeight / 2);
        ball = new Ball(screenWidth / 2, screenHeight / 2);
        score1 = new Score(screenWidth / 2 - 80);
        score2 = new Score(screenWidth / 2 + 60);

        gameWindow.addDrawable(paddle1);
        gameWindow.addDrawable(paddle2);
        gameWindow.addDrawable(ball);
        gameWindow.addDrawable(score1);
        gameWindow.addDrawable(score2);

        gameWindow.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
            paddle1.up();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            paddle1.down();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            paddle2.up();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.down();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_W || keyEvent.getKeyCode() == KeyEvent.VK_S) {
            paddle1.stop();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP || keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.stop();
        }
    }

    public void start() {
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 1000 / 120); // ~120 FPS (janky at 60 FPS)
    }

    private void update() {
        paddle1.handleScreenCollision(0, screenHeight);
        paddle2.handleScreenCollision(0, screenHeight);

        final int screenCollision = ball.handleScreenCollision(0, 0, screenWidth, screenHeight);
        if (screenCollision == -1) {
            score2.addPoint();
        } else if (screenCollision == 1) {
            score1.addPoint();
        }

        ball.checkCollision(paddle1.x, paddle1.y, paddle1.x + Paddle.WIDTH, paddle1.y + Paddle.HEIGHT);
        ball.checkCollision(paddle2.x, paddle2.y, paddle2.x + Paddle.WIDTH, paddle2.y + Paddle.HEIGHT);

        paddle1.update();
        paddle2.update();
        ball.update();

        gameWindow.repaint();
    }

    private static class GameWindow extends JFrame {

        private final GameCanvas gameCanvas = new GameCanvas();

        public GameWindow() {
            super("Pong!");
            add(gameCanvas);
            setSize(new Dimension(624, 351));
            setResizable(false);
            setVisible(true);
            setBackground(Color.BLACK);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void addDrawable(Drawable drawable) {
            gameCanvas.addDrawable(drawable);
        }
    }

    private static class GameCanvas extends JPanel {
        private final List<Drawable> drawables = new ArrayList<>();

        @Override
        public void paint(Graphics graphics) {
            drawLine(graphics);
            drawDrawables(graphics);
        }

        private void drawLine(Graphics graphics) {
            graphics.setColor(Color.WHITE);
            graphics.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        }

        private void drawDrawables(Graphics graphics) {
            for (Drawable drawable : drawables) {
                drawable.draw(graphics);
            }
        }

        public void addDrawable(Drawable drawable) {
            drawables.add(drawable);
        }
    }
}
