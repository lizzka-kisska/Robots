package gui;

import logic.Robot;
import logic.Target;
import logic.UserRobot;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class GameVisualizer extends JPanel {
    Robot robot = new Robot(100, 100, 0);
    Target target = new Target(150, 100);
    UserRobot userRobot = new UserRobot(150, 150, 0);
    private Timer m_timer = initTimer();
    private long startTime;
    private boolean needTime = true;
    private boolean runGame = true;

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    public GameVisualizer() {
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                target.setTargetPosition(e.getPoint().x, e.getPoint().y);
                repaint();
            }
        });
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                userRobot.changeDirection(e);
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    protected void onModelUpdateEvent() {
        if (runGame){
            robot.moveRobot(getWidth(), getHeight(), target.xCoordinate, target.yCoordinate);
            userRobot.moveUserRobot(getWidth(), getHeight());
            if (userRobot.xOffset != 0 || userRobot.yOffset != 0){
                checkDistance();
            }
        }
    }

    protected void checkDistance() {
        if (Math.sqrt(Math.pow(robot.xCoordinate - userRobot.xCoordinate, 2)
                + Math.pow(robot.yCoordinate - userRobot.yCoordinate, 2)) <= 100) {
            if (needTime) {
                startTime = System.currentTimeMillis();
                needTime = false;
            }
            MainApplicationFrame.timerWindow.setTime(
                        Long.toString(2 - (System.currentTimeMillis() - startTime) / 1000));
            if (System.currentTimeMillis() - startTime >= 2000) {
                MainApplicationFrame.timerWindow.setText("LOSE");
                runGame = false;
            }
        } else {
            needTime = true;
            MainApplicationFrame.timerWindow.setText("HYPE");
        }
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, round(robot.xCoordinate), round(robot.yCoordinate), robot.direction, Color.RED);
        drawRobot(g2d, round(userRobot.xCoordinate), round(userRobot.yCoordinate), userRobot.direction, Color.CYAN);
        drawTarget(g2d, target.xCoordinate, target.yCoordinate);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, int x, int y, double direction, Color bodyColor) {
        int robotCenterX = round(x);
        int robotCenterY = round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(bodyColor);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
